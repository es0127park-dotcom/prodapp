package client;

import java.io.*;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) {
        try {
            // 1. Socket 연결 완료
            Socket socket = new Socket("localhost", 20000);

            // 2. 키보드 입력 버퍼
            InputStream keyStream = System.in;
            InputStreamReader keyReader = new InputStreamReader(keyStream);
            BufferedReader keyBuf = new BufferedReader(keyReader);

            // 3. BW 버퍼
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            // 4. BR 버퍼
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            while(true){
                // 요청
                String keyboardData = keyBuf.readLine();
                bw.write(keyboardData);
                bw.write("\n");
                bw.flush();

                // 응답
                String line = br.readLine();
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
