/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chat.Server;

/**
 *
 * @author LG
 */
import java.net.*; // ServerSocket, Socket
import java.io.*;  // 입출력
import java.util.Vector;

public class Server {

    // 클라이언트와 연결할 때만 필요한 ServerSocket 클래스
    ServerSocket serversocket;

    // 서버로 접속한 클라이언트 Socket을 저장할 멤버 변수
    Socket socket;

    // 접속 클라이언트 정보 실시간 저장
    Vector v;

    ServerThread st;

    public Server() {
        // 사용자 정보를 담을 v를 Vector 객체로 초기화
        v = new Vector();
        try {
            // ServerSocket 객체 생성 → 포트 번호 생성(임의의 번호 부여)
            serversocket = new ServerSocket(5432);
            System.out.println("ss>>>" + serversocket);
            System.out.println("채팅 서버 가동중...");

            // 서버 가동: 클라이언트가 접속할 때까지 기다리는 것(무한 대기)
            while (true) {
                // 접속 클라이언트 Socket을 s 변수에 저장
                socket = serversocket.accept();
                System.out.println("Accepted from" + socket);

                // 접속 클라이언트와 서버로 st객체 생성
                st = new ServerThread(this, socket);

                // 접속할 때마다 v에 접속 클라이언트 스레드 추가
                this.addThread(st);

                // Thread 가동 -> run() -> broadCast() -> send() 실시간 메소드 호출
                st.start();
            }

        } catch (Exception e) {
            // 접속 실패시 간단한 Error 메세지 출력
            System.out.println("서버 접속 실패>>>" + e);
        }
    }

    // 벡터 v에 접속 클라이언트의 스레드 저장
    public void addThread(ServerThread st) {
        v.add(st);
    }

    // 퇴장한 클라이언트 스레드 제거
    public void removeThread(ServerThread st) {
        v.remove(st);
    }

    // 각 클라이언트에게 메세지를 출력하는 메소드, send() 호출
    public void broadCast(String str) {
        for (int i = 0; i < v.size(); i++) {
            // 각각의 클라이언트를 ServerThread 객체로 형 변환 
            ServerThread st = (ServerThread) v.elementAt(i);

            // 각 스레드 객체에 str 문자열을 전송
            st.send(str);
        }
    }
}
