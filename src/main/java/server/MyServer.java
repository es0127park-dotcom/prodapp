package server;

import com.google.gson.Gson;
import dto.RequestDTO;
import dto.ResponseDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class MyServer {
    public static void main(String[] args) {
        try {
            // 1. 20000번 포트로 대기중...
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 2. 새로운 소켓에 버퍼달기 (BR, BW)
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            while (true) {
                // 1. 클라이언트로부터 받은 메시지
                String line = br.readLine(); // 엔터키까지 읽기

                // 2. 파싱 (json string -> object)
                Gson gson = new Gson();
                RequestDTO req = gson.fromJson(line, RequestDTO.class);

                // 3. 서비스호출
                ProductService ps = new ProductService();

                // 3-1. 상품 등록(호출 -> 응답) 성공
                if (req.getMethod().equals("post")) {
                    ps.상품등록(req.getBody().getName(), req.getBody().getPrice(), req.getBody().getQty());

                    // 응답
                    ResponseDTO<Product> resp = new ResponseDTO<>("ok", null);
                    String json = gson.toJson(resp);
                    bw.write(json);
                    bw.write("\n");
                    bw.flush();
                }

                // 3-2. 상품 목록(호출 -> 응답) 성공
                if (req.getMethod().equals("get") && req.getQuerystring() == null) {
                    List<Product> products = ps.상품목록();

                    // 응답
                    ResponseDTO<List<Product>> resp = new ResponseDTO<>("ok", products);
                    String json = gson.toJson(resp);
                    bw.write(json);
                    bw.write("\n");
                    bw.flush();
                }

                // 3-3. 상품 상세(호출 -> 응답) 성공
                if (req.getMethod().equals("get") && req.getQuerystring() != null) {
                    try {
                        Product product = ps.상품상세(req.getQuerystring().get("id"));

                        // 응답
                        ResponseDTO<Product> resp = new ResponseDTO<>("ok", product);
                        String json = gson.toJson(resp);
                        bw.write(json);
                        bw.write("\n");
                        bw.flush();
                    } catch (RuntimeException e) {
                        ResponseDTO<Product> resp = new ResponseDTO<>(e.getMessage(), null);
                        String json = gson.toJson(resp);
                        bw.write(json);
                        bw.write("\n");
                        bw.flush();
                    }
                }

                // 3-4. 상품 삭제(호출 -> 응답) 성공
                if (req.getMethod().equals("delete")) {
                    ps.상품삭제(req.getQuerystring().get("id"));

                    // 응답
                    ResponseDTO resp = new ResponseDTO("ok", null);
                    String json = gson.toJson(resp);
                    bw.write(json);
                    bw.write("\n");
                    bw.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
