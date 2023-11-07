package calculator;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Properties;

public class CalcClientEx {
    public static void main(String[] args) {
        BufferedReader in = null;
        BufferedWriter out = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // 클라이언트 설정 파일 (client.txt) 읽기
            Properties properties = new Properties();
            
            //txt 텍스트 파일이 저장된 절대경로를 넣었음.. 
            String path = "C:\\\\Users\\\\gkds0\\\\eclipse-workspace\\\\network_socket\\\\src\\\\calculator\\\\client.txt";
            
            try (FileInputStream configFile = new FileInputStream(path)) {
                properties.load(configFile);
            } catch (IOException e) {
                System.out.println("클라이언트 설정 파일을 읽을 수 없습니다.");
                return;
            }
            
            //메모장에 저장된 server_ip 가져오기
            String serverIp = properties.getProperty("server_ip");
            int serverPort = 9999; // 서버 포트 번호

            socket = new Socket(serverIp, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            while (true) {
                System.out.print("계산식(빈칸으로 띄어 입력, 예: add 24 42) / 종료 시 bye 입력>>"); // 프롬프트
                String outputMessage = scanner.nextLine(); // 키보드에서 수식 읽기
                if (outputMessage.equalsIgnoreCase("bye")) {
                    out.write(outputMessage + "\n"); // "bye" 문자열 전송
                    out.flush();
                    break; // 사용자가 "bye"를 입력한 경우 서버로 전송 후 연결 종료
                }
                out.write(outputMessage + "\n"); // 키보드에서 읽은 수식 문자열 전송
                out.flush();
                String inputMessage = in.readLine(); // 서버로부터 계산 결과 수신
                System.out.println(inputMessage);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if (socket != null)
                    socket.close(); // 클라이언트 소켓 닫기
            } catch (IOException e) {
                System.out.println("서버와 채팅 중 오류가 발생!");
            }
        }
    }
}
