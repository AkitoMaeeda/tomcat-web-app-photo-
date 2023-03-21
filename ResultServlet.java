import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import java.awt.image.*;
import javax.imageio.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebServlet("result")
@MultipartConfig(location = "/image", // ファイルを一時的に保存するパス
        maxFileSize = 10000000, maxRequestSize = 10000000, fileSizeThreshold = 10000000)

public class ResultServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setCharacterEncoding("utf-8");
        Part part = request.getPart("myFiles");

        String filename = part.getSubmittedFileName();

        String path = "/image";
        String filepath = path + filename;

        System.out.println(path);

        part.write("D:/image/" + filename);// ファイルの保存

        File article = new File(filepath);
        int a = 90;
        Photo ph = new Photo(a);// 写真のオブジェクト作成

        String rpath = ph.generat(article); // 加工後の写真のパスを取得

        request.setAttribute("photo", filename);
        request.setAttribute("filepath", "ぱす");

        request.setAttribute("message", "送信完了");

        String view = "/WEB-INF/views/result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(view);
        dispatcher.forward(request, response);
    }
}

class Photo {

    int s;
    String rpath;

    // 引数から受け取ったパスと彩度をだいにゅーするよん
    Photo(int c) {
        s = c;
    }

    // 加工した写真の作成
    String generat(File f) {

        try {
            BufferedImage bi = ImageIO.read(f);
            int w = bi.getWidth();
            int h = bi.getHeight();
            BufferedImage writebi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            LocalDateTime cl = LocalDateTime.now();

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int c = bi.getRGB(x, y);

                    int r = (c & 0x00ff0000) >> 16;
                    int g = (c & 0x0000ff00) >> 8;
                    int b = c & 0x000000ff;

                    final int[] add = HSVtrans(r, g, b, s);

                    r = add[0];
                    if (r > 0xff)
                        r = 0xff;
                    g = add[1];
                    if (g > 0xff)
                        g = 0xff;
                    b = add[2];
                    if (b > 0xff)
                        b = 0xff;
                    c = 0xff000000 | (r << 16) | (g << 8) | b;

                    writebi.setRGB(x, y, c);
                }
            }
            System.out.println(cl);
            rpath = "image/" + cl.getMonth() + "_" + cl.getDayOfMonth() + "_" + cl.getSecond() + ".jpg";
            ImageIO.write(writebi, "jpg",
                    new File(rpath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rpath;
    }

    // RGBの値を彩度に変換して加工後の値を求める
    private static int[] HSVtrans(int r, int g, int b, int s) {

        int[] rgb = { r, g, b };

        // Rが一番高い数値の時

        if (rgb[1] >= rgb[2] && rgb[0] >= rgb[1]) {// R>G>B
            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;// 彩度の数値を求める

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }

            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);// 小さい色の傾き

            double basic3 = basic1 / S;// 2番目の色の傾き

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            int temp = rgb[0];
            rgb[0] = rgb[2];
            rgb[2] = temp;

            return rgb;
        }

        if (rgb[2] >= rgb[1] && rgb[0] >= rgb[2]) {// R>B>G

            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }

            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);

            double basic3 = basic1 / S;

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            int temp = rgb[1];
            rgb[1] = rgb[0];
            int temp2 = rgb[2];
            rgb[2] = temp;
            rgb[0] = temp2;
            return rgb;
        }

        // Gが一番高い時
        if (rgb[1] >= rgb[0] && rgb[0] >= rgb[2]) {// G>R>B
            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }
            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);

            double basic3 = basic1 / S;

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            int temp = rgb[1];
            rgb[1] = rgb[0];
            rgb[2] = temp;

            return rgb;
        }

        if (rgb[1] >= rgb[2] && rgb[2] >= rgb[0]) {// G>B>R
            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }

            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);

            double basic3 = basic1 / S;

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            int temp = rgb[1];
            rgb[1] = rgb[2];
            rgb[2] = temp;

            return rgb;
        }

        // Bが一番高い時
        if (rgb[2] >= rgb[0] && rgb[0] >= rgb[1]) {// B>R>G
            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;// 彩度の数値を求める

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }

            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);

            double basic3 = basic1 / S;

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            int temp = rgb[1];
            rgb[1] = rgb[0];
            rgb[0] = temp;

            return rgb;
        }

        if (rgb[2] >= rgb[1] && rgb[1] >= rgb[0]) {// B>G>B
            Arrays.sort(rgb);
            double S = ((double) rgb[2] - (double) rgb[0]) / rgb[2] * 100;
            double H = rgb[2];

            if (0 < s) {
                s = (int) (S + ((100 - S) * s * 1 / 100));
            }
            if (0 > s) {
                s = (int) (S - Math.abs(S * s * 1 / 100));
            }

            double basic1 = rgb[2] - rgb[1];

            double basic2 = rgb[0] / (100 - S);

            double basic3 = basic1 / S;

            rgb[0] = (int) (rgb[0] - (s - S) * basic2);

            rgb[1] = (int) (rgb[1] - (s - S) * basic3);

            return rgb;
        }

        return rgb;

    }
}
