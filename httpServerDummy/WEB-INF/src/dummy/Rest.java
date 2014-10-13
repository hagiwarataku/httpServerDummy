package dummy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Rest extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// レスポンスファイルパス（ローカル環境に合わせて設定）
	private static final String RESPONSE_FILE_PATH = "C:/test/httpclientTest/response.txt";
	// リクエストBODYサイズ（足りなかったら増やす）
	private static final int REQUEST_SIZE = 2048;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{

		echoStart(request);
		echoHeader(request);
		setResMessage(response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{

		echoStart(request);
		echoHeader(request);
		echoBody(request);
		setResMessage(response);

	}

	/**
	 * レスポンスメッセージ作成
	 * @param response HTTPレスポンス
	 */
	public void setResMessage(HttpServletResponse response) {

		PrintWriter out = null;
		BufferedReader br = null;
		response.setContentType("text/plain;charset=utf-8");
		try {
			out = response.getWriter();
			br= new BufferedReader(new InputStreamReader(new FileInputStream(RESPONSE_FILE_PATH),"SJIS"));
			String str = "";
			while ((str = br.readLine()) != null) {
				out.println(str);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 開始メッセージ出力
	 * @param request HTTPリクエスト
	 */
	public void echoStart(HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        String strDate = sdf.format(cal.getTime());
		String path = request.getRequestURI();

		System.out.println("********************** API Start********************");
		System.out.println(strDate + " [" + path + "]");
	}

	/**
	 * HEDER部出力
	 * @param request HTTPリクエスト
	 */
	public void echoHeader(HttpServletRequest request) {

		System.out.println("********************** HEADER **********************");
		Enumeration headernames = request.getHeaderNames();
		while (headernames.hasMoreElements()){
		      String name = (String)headernames.nextElement();
		      Enumeration headervals = request.getHeaders(name);
		      while (headervals.hasMoreElements()){
		        String val = (String)headervals.nextElement();
		        System.out.println(name + ":" + val);
		      }
		}

	}

	/**
	 * BODY部出力
	 * @param request HTTPリクエスト
	 */
	public void echoBody(HttpServletRequest request) {

		System.out.println("********************** BODY   **********************");
		 try {
			ServletInputStream sis = request.getInputStream();
			byte[] bytes = new byte[REQUEST_SIZE];
			sis.read(bytes,0,REQUEST_SIZE);
			System.out.println("UTF-8***********************************************");
			System.out.println(new String(bytes, "UTF-8"));
			System.out.println("SJIS************************************************");
			System.out.println(new String(bytes, "SJIS"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}
}
