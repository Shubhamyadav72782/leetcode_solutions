/*class Solution {
    public int divide(int dividend, int divisor) {

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        int result = 0;

        while (dvd >= dvs) {
            long temp = dvs;
            int multiple = 1;

            while (dvd >= (temp << 1)) {
                temp <<= 1;
                multiple <<= 1;
            }

            dvd -= temp;
            result += multiple;
        }
        if ((dividend > 0) ^ (divisor > 0)) {
            return -result;
        }

        return result;
    }
}*/






class Solution {
    public int divide(int dividend, int divisor) {
        if(dividend == Integer.MIN_VALUE && divisor == -1){
            return Integer.MAX_VALUE;
        }
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);

        long left = 0;
        long right = dvd;
        long ans = 0;

        while(left <= right){
            long mid = left + (right - left)/2;
            
            long product = multiply(mid, dvs);

            if(product <= dvd){
                ans = mid;
                left = mid + 1;
            }else{
                right = mid -1;
            }
        }

            if((dividend < 0) ^ (divisor < 0)){
                ans = -ans;
            }
        
        return (int) ans;

    }

    public long multiply(long a, long b){
        long result = 0;
        while(b >0){
        if((b & 1) == 1){
            result += a;
        }
        a <<= 1;
        b >>= 1;
        }
        return result;
    }
}