package ru.unclediga.book.wsuprun.ch01;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class NanoSrv extends NanoHTTPD {

        public static final String RESP = "NanoHTTPD";
        public NanoSrv() throws IOException {
            super(8081);
            // start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            start(1,false);
            System.out.println("\nRunning! Point your browsers to http://localhost:8081/ \n");
        }
    
        @Override
        public Response serve(IHTTPSession session) {
            String msg = RESP;
            // Map<String, String> parms = session.getParms();
            // if (parms.get("username") == null) {
            //     msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
            // } else {
            //     msg += "<p>Hello, " + parms.get("username") + "!</p>";
            // }
            // return newFixedLengthResponse(msg + "</body></html>\n");
            return newFixedLengthResponse(msg);
        }
    }