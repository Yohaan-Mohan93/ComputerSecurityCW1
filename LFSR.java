import java.util.Vector;
import java.io.FileNotFoundException;
import org.json.JSONObject;
import org.json.JSONException;


public class LFSR {
	static Vector<Integer> tap;
	
	public static void BMAlgorithm(byte[]seed) {
		int L, N, m, d;
        int n = seed.length;
        byte[] c = new byte[n];
        byte[] b = new byte[n];
        byte[] t = new byte[n];

        b[0]= c[0] = 1;
        L = N = 0;
        m = -1;
                
        while (N < n){
            d = 0;
            for (int i = 0; i <= L; i++)
            d ^= c[i] & seed[N - i];            
            if (d == 1){
            	System.arraycopy(c, 0, t, 0, n);
                for (int i = 0; (i + N - m) < n; i++)
                    c[i + N - m] ^= b[i];
                if (L <= N / 2) {
                    L = N + 1 - L;
                    m = N;
                    System.arraycopy(t, 0, b, 0, n);
                }
            }
            N++;
        }
        
        for(int i = 1; i < c.length; i++) {
        	if((int)c[i] == 1)
        		tap.add(i);
        }
	}
	
	public static void main(String[] args) throws FileNotFoundException, JSONException{
		JSONObject obj = readJSON.readFile("YohaanMohan_160291137_CO3326_cw1.json");
		String keyFrag = obj.getString("keyFragment");
		
		byte[] fragment = new byte[keyFrag.length()];
		for(int i = 0; i < fragment.length; i++) {
			if(keyFrag.charAt(i) == '1')
				fragment[i] = (byte)1;
			else
				fragment[i] = (byte)0;
		}
		
		tap = new Vector<Integer>();
		BMAlgorithm(fragment);
		System.out.println(tap);
	}
}
