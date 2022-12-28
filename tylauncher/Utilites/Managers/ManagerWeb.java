package tylauncher.Utilites.Managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ManagerWeb {
    private URL url;
    private String requestMethod;
    private final Map<String, String> _params = new HashMap<>();
    private String requestType;

    private String[] answerMass;
    private String answer;
    private int connectTimeout;
    public ManagerWeb(String type){
        connectTimeout = 1000;
        url = null;
        requestMethod = "POST";
        this.requestType = type;
    }

    public void request() throws Exception {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod(requestMethod);
        httpURLConnection.setDoOutput(true);
        StringJoiner stringJoiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : _params.entrySet())
            stringJoiner.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = stringJoiner.toString().getBytes(StandardCharsets.UTF_8);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpURLConnection.setConnectTimeout(connectTimeout);
        httpURLConnection.connect();
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            outputStream.write(out);
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                line = bufferedReader.readLine()
                        .replace("\"", "")
                        .replace("}", "")
                        .replace("{", "")
                        .replace("]", "")
                        .replace("[", "");
                if(line.contains("<br />")) throw new Exception("Сайт лёг. Обратитесь к администрации!");
                this.answer = line;
                this.answerMass = line.split("[,:]");
            }else throw new Exception("Ошибка сервера(" + httpURLConnection.getResponseCode() + "). Обратитесь к администрации!");
        }
    }

    public void putParam(String key, String value){
        _params.put(key, value);
    }
    public URL getUrl() {
        return url;
    }
    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
    public String[] getAnswerMass(){
        return this.answerMass;
    }
    public String getAnswer(){
        return this.answer;
    }

    public void setConnectTimeout(int millsec){
        this.connectTimeout = millsec;
    }

    @Override
    public String toString() {
        return "ManagerWeb{" +
                "  url=" + url +
                ", requestMethod='" + requestMethod + '\'' +
                ", _params=" + _params +
                ", requestType='" + requestType + '\'' +
                ", answer=" + answer +
                ", answerMass=" + Arrays.toString(answerMass) +
                ", connectTimeout=" + connectTimeout +
                '}';
    }
}
