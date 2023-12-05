/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat.Server;

import java.io.*;
import java.net.*;

/**
 *
 * @author LG
 */
public class ServerThread extends Thread {

    Socket socket;// 클라이언트 소켓 저장
    Server server;// ChatGUIServer 클래스의 객체를 멤버 변수로 선언, has-a 관계를 위함
    
    BufferedReader br;
    PrintWriter pw;
    
    String serverText;// 전달할 문자열
    String name;// 닉네임

    public ServerThread(Server server, Socket socket) {
        this.server = server;

        // 접속한 클라이언트 정보 저장
        this.socket = socket;

        // 데이터 전송을 위한 입출력 스트림 생성
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("에러 발생>>>>>" + e);
        }
    }
    public void send(String str) {
        pw.println(str);

        pw.flush();// 혹시나 버퍼에 남아있는 것을 비워냄
    }
    public void run() {
        try {
            pw.println("닉네임을 입력하세요");
            name = br.readLine();

            // 서버에서 각 클라이언트에 대화명 출력
            server.broadCast("[" + name + "]" + "님이 입장했습니다.");

            // 무한 대기하며 입력한 메세지를 각 클라이언트에 계속 전달
            while ((serverText = br.readLine()) != null) {
                server.broadCast("[" + name + "]: " + serverText);
            }
        } catch (Exception e) {
            // 접속자 퇴장시 v에서 해당 클라이언트 스레드 제거
            server.removeThread(this); // this: ServerThread 객체, 접속 클라이언트
             // 서버에서 각 클라이언트에 출력
            server.broadCast("[" + name + "]" + "님이 퇴장했습니다.");

            // 콘솔에 퇴장 클라이언트 IP 주소 출력
            System.out.println(socket.getInetAddress() + "의 연결이 종료됨!");
        }
    }
}
