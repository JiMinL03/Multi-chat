/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chat;
import com.mycompany.chat.Server.Server;
/**
 *
 * @author LG
 */
public class CHAT {

    public static void main(String[] args) {    
        Server server = new Server();
        ChattingScreen a = new ChattingScreen();
        a.setVisible(true);
    }
}
