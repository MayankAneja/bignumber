import java.io.*;
import java.util.*;
public class irreducible_poly{
	static float[] primeFactors(float n){
		Vector<Float> v= new Vector<Float>(); 
		float num=n;
		if(n%2==0){
			v.add((float)2);		//add 2 to vector
		}
		while (n%2 == 0){
			// System.out.println("ji"+n);
			n = (float)n/2;
			// System.out.println(n);

		}
		for (float i = 3; i <= Math.sqrt(num)+1; i = i+2){
			// System.out.println(n);
			if(n%i==0){
				v.add((float)i);
				// System.out.println("ko");
			}
			while (n%i == 0){
				// System.out.println(n);
				n = (float)n/i;
			}
		}

	// This condition is to handle the case whien n is a prime number
	// greater than 2
		if (n > 2){
			v.add(n);
			//add n to list
			// printf ("%d ", n);
		}
		float[] prime_arr=new float[v.size()];
		for(int i=0;i<v.size();i++){
			// System.out.println(v.get(i));
			prime_arr[i]=v.get(i);
		}
		return prime_arr;
	}
	static float gcd_2(float a,float b){
		float float1=(a>b)?a:b;
		float float2=(a<b)?a:b;
		float1=(float1>0)?float1:-1*float1;
		float2=(float2>0)?float2:-1*float2;
		
		if(float2==0){
			return float1;
		}
		else {
			return gcd_2(float2,float1 % float2);
		}
	}
	static float gcd(float[] arr){
		if(arr.length==2){
			return gcd_2(arr[0],arr[1]);
		}
		else if(arr.length==1){
			float float1=(arr[0]>0)? arr[0]:-1*arr[0];
			return float1;
		}
		else{
			float[] part1=new float[arr.length/2];
			for(int i=0;i<arr.length/2;i++){
				part1[i]=arr[i];
			}
			float[] part2=new float[arr.length-arr.length/2];
			for(int i=arr.length/2;i<arr.length;i++){
				part2[i-arr.length/2] = arr[i];
			}
			return gcd_2(gcd(part1),gcd(part2));
		}
	}
	static float[] divide_by_gcd(float[] arr){

		float[] ans=new float[arr.length];
		float gcd_arr=gcd(arr);
		for(int i=0;i<arr.length;i++){
			ans[i]=(float)arr[i]/gcd_arr;
		}
		for(int i=0;i<arr.length;i++){
			if(arr[i]/gcd_arr>1.0E40){
				return arr;
			}
		}
		return ans;
	}
	static void print_poly(float[] poly){
		String temp_str="";
		String sign; 
		for(int i=poly.length-1;i>=0;i--){
			if(poly[i]!=0){
				if(poly[i]>0){sign="+";}
				else {sign = "";}
				if(i!=0){
					if(poly[i]==1){temp_str=temp_str.concat(sign+"x^"+i);}
					else{temp_str=temp_str.concat(sign+(int)poly[i]+"x^"+i);}
				}
				else{
					temp_str=temp_str.concat(sign+(int)poly[i]+"x^"+i);
				}
			}
		}
		if(temp_str.contains("x^0")){temp_str=temp_str.replace("x^0","");}
		if(temp_str.length()>1){
			if(temp_str.charAt(0)=='+' ){ System.out.println(temp_str.substring(1,temp_str.length())); }
			else{System.out.println(temp_str);}
		}
		else{
			System.out.println("0");
		}
	}

	static float[] last_cut(float[] poly1){
		int poly1_len=poly1.length;
		float[] ans=new float[poly1_len-1];
		for(int i=0;i<poly1_len-1;i++){
			ans[i]=poly1[i];
		}
		return ans;
	}
	
	static boolean equal(float[] poly1,float[] poly2){
		int poly1_len = poly1.length;
		int poly2_len = poly2.length;
		if(poly1_len!=poly2_len){return false;}
		else{
			if(poly1_len==1 && poly2_len==1){return poly1[0]==poly2[0];}
			if(poly1[poly1_len-1]!=poly2[poly2_len-1]){return false;}
			if(poly1[poly1_len-1]==poly2[poly2_len-1]){
				return equal(last_cut(poly1),last_cut(poly2));
			}
		}
		return true;
	}
	
	static boolean less_than(float[] poly1,float[] poly2){
		int poly1_len = poly1.length;
		int poly2_len = poly2.length;
		if(poly1_len>poly2_len){return false;}
		else if(poly1_len<poly2_len){return true;}
		else if(poly1_len==poly2_len){
			if(poly1_len==1 && poly2_len==1){return poly1[0]<poly2[0];}
			if(poly1[poly1_len-1]==poly2[poly2_len-1]){
				return less_than(last_cut(poly1),last_cut(poly2));
			}
			else return (poly1[poly1_len-1]<poly2[poly2_len-1]);
		}
		return false;
	}
	
	static float[] remove_zeros(float[] poly1){
		int ans_len=0;
		for(int i=poly1.length-1;i>=0;i--){
			if(poly1[i]==0){ans_len++;}
			else { break; }
		}
		if(ans_len==poly1.length){
			float[] ans = new float[1];
			ans[0]=0;
			return ans;
		}
		float[] ans = new float[poly1.length - ans_len];
		for(int t=0;t<poly1.length-ans_len;t++){
			ans[t] = poly1[t];
		}
		return ans;
	}

	static float[] make_same(float[] poly1, int len){
		float[] ans = new float[len];
		for (int i=0;i<poly1.length;i++){
			ans[i] = poly1[i];
		}
		if(poly1.length>len){
			for (int i=poly1.length;i<len;i++){
				ans[i]=0;
			}
		}
		return ans;
	}

	static float[] sub(float[] poly1, float[] poly2,int modulus){
		int poly1_len = poly1.length;
		int poly2_len = poly2.length;
		int max_len = (poly1_len<poly2_len)? poly2_len:poly1_len;
		float[] ans = new float[max_len];
		if(poly1.length>poly2.length){poly2 = make_same(poly2,max_len);}
		if(poly1.length<poly2.length){poly1 = make_same(poly1,max_len);}
		for(int i=0;i<max_len;i++){
			ans[i] = ((poly1[i]-poly2[i]) % modulus +modulus)%modulus;
		}
		ans = remove_zeros(ans);
		return ans;
	}

	static float[] add(float[] poly1, float[] poly2,int modulus){
		int poly1_len = poly1.length;
		int poly2_len = poly2.length;
		int max_len = (poly1_len<poly2_len)? poly2_len:poly1_len;
		float[] ans = new float[max_len];
		if(poly1.length>poly2.length){poly2 = make_same(poly2,max_len);}
		if(poly1.length<poly2.length){poly1 = make_same(poly1,max_len);}
		for(int i=0;i<max_len;i++){
			ans[i] = ((poly1[i]+poly2[i]) % modulus + modulus) % modulus;
		}
		ans = remove_zeros(ans);
		return ans;
	}

	static float[] mod(float[] poly1,float[] poly2,int modulus){
		int poly1_len = poly1.length;
		int poly2_len = poly2.length;
		int max_len = (poly1_len<poly2_len)? poly2_len:poly1_len;
		// float[] ans;
		if( poly1.length<poly2.length ){return poly1;}
		else{
			// System.out.println("poly1");
			// print_poly(poly1);
			// System.out.println("poly2");
			// print_poly(poly2);
			float[] poly2_n = new float[max_len];
			if(poly1.length>=poly2.length){ poly2_n = make_same(poly2,max_len);}
			float first_co_poly1 = poly1[poly1_len-1];
			float first_co_poly2 = poly2[poly2_len-1];
			// System.out.println(poly1[poly1_len-1]);
			float[] poly2_new = new float[max_len];
			for(int i=0;i<poly2.length;i++){
				poly2_new[i+max_len-poly2.length] = (first_co_poly1/first_co_poly2)*poly2[i];
				// System.out.println((float)poly2_new[i+max_len-poly2.length]);
				// System.out.println(i+max_len-poly2.length);
			}
			if(max_len-poly2.length>0){
				for(int j=0;j<max_len-poly2.length;j++){
					poly2_new[j]=0;
				}
			}
			// System.out.println("poly1->");
			// print_poly(poly1);
			// System.out.println("poly2_new->");
			// print_poly(poly2_new);
			float[] ans = sub(poly1,poly2_new,modulus);
			// System.out.println("ans");
			// print_poly(ans);
			// float[] ans_2 =(remove_zeros(ans ) );
			if(ans.length==1 && ans[0]==0){ return ans;}
			else{return mod(ans,poly2,modulus);}
		}
		// return ans;
	}

	static float[] gcd_simple(float[] poly1, float[] poly2,int modulus){
		float[] mod_ans = mod(poly1,poly2,modulus);
		if(mod_ans.length==1 && mod_ans[0]==0){
			return (poly2);
		}
		return gcd_simple( poly2 , mod_ans ,modulus );
	}

	static float[] gcd(float[] poly1,float[] poly2,int modulus){
		if(less_than(poly1,poly2)){return gcd_simple(poly2,poly1,modulus);}
		else {return gcd_simple(poly1,poly2,modulus);}
	}

	public static void main(String args[]){
		Scanner s=new Scanner(System.in);
		System.out.println("Enter the degree");
		int degree=s.nextInt();
		System.out.println("Enter the modulus");
		int modulus=s.nextInt();
		int max_deg=(int)Math.pow(modulus,degree);
		float[] poly1=new float[max_deg+1];
		for(int i=0;i<max_deg+1;i++){
			poly1[i]=0;
		}
		int count=0;
		poly1[1]=-1;
		poly1[max_deg]=1;
		float[] poly2=new float[degree+1];
		poly2[degree]=1;

		float[] one=new float[1];
		one[0]=1;
		float[] temp=new float[3];
		temp[0]=0;
		temp[1]=-1;
		temp[2]=1;
		float[] poly_temp;
		boolean final_ans=true;
		float[] prime_arr = primeFactors(degree);
		// System.out.println(prime_arr.length);
		for(int i=0;i<max_deg;i++){
			final_ans=true;
			for(int l=0;l<degree;l++){
				poly2[l]=(float)(i/(int)Math.pow(modulus,l)) % modulus;
			}
			if(equal(gcd(poly1,poly2,modulus),poly2) ){
				
				for(int j=0;j<prime_arr.length;j++){
					int max_deg_1=(int)Math.pow(modulus,degree/(int)prime_arr[j]);
					// System.out.println("max_deg"+max_deg_1);
					poly_temp=new float[max_deg_1+1];
					for(int k=0;k<max_deg_1+1;k++){
						poly_temp[k]=0;
					}
					poly_temp[1]=-1;
					poly_temp[max_deg_1]=1;
					if(!equal(gcd(poly_temp,poly2,modulus),one)){
						final_ans=false;
						break;
					}
				}
				if(final_ans){
					count++;
					print_poly(poly2);
				}
			}
		}
		System.out.println("Number of irreducible polynomials with degree "+degree+" and modulus "+modulus+" is "+count+".");
	}
}