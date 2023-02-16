package tylauncher.Managers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.IntStream;

public class ManagerWeb {

    private final Map<String, String> _params;
    private final String _requestType;
    private URL _url;
    private String _requestMethod;
    private String _answer;
    private int _connectTimeout;

    public ManagerWeb(String type) {
        this._connectTimeout = 3000;
        this._url = null;
        this._requestMethod = "POST";
        this._requestType = type;
        this._params = new HashMap<>();
    }

    /**
     * Функция просто подключается к серверу, скидывает ему запрос(POST или GET, по умолчанию - POST)
     * и получает ответ(считывает 1 строку, если же ошибка скрипта, то считывает все строки)
     * Ответ преобразовывает в массив, так же оставляет в виде строки.
     *
     * @throws Exception Возникает при ошибке со стороны сервера(Сервер сдох, респонс код не ок). Ошибка при этом выводится в консоль.
     */
    public void request() throws Exception {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        URLConnection urlConnection = _url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod(_requestMethod);
        httpURLConnection.setDoOutput(true);
        StringJoiner stringJoiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : _params.entrySet())
            stringJoiner.add(URLEncoder.encode(entry.getKey(), "UTF-8")
                    + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = stringJoiner.toString().getBytes(StandardCharsets.UTF_8);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpURLConnection.setConnectTimeout(_connectTimeout);
        httpURLConnection.connect();
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            outputStream.write(out);
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String line;

                this._answer = bufferedReader.readLine();
                if (this._answer.contains("<br />") || this._answer.trim().contains("Error:")) {
                    System.err.println(this._answer);
                    while ((line = bufferedReader.readLine()) != null) System.err.println(line);
                    throw new Exception("Сайт лёг. Обратитесь к администрации!(Больше информации в логах)");
                }
            } else
                throw new Exception("Ошибка сервера(" + httpURLConnection.getResponseCode() + "). Обратитесь к администрации!");
        }
    }

    /**
     * В общем это пизда..........
     * используется метод range из класса IntStream, чтобы создать поток целых чисел от 0 до размера списка keys.
     * Этот поток используется для итерации( Итерация - это процесс перебора элементов в некоторой коллекции
     * или последовательности данных.) по индексам элементов списков keys и values.
     * Наконец, метод forEach используется для выполнения лямбда-выражения для каждого элемента потока. Лямбда-выражение
     * вызывает функцию putParam с аргументами, которые соответствуют элементу списка keys с текущим индексом и элементу
     * списка values с текущим индексом.
     *
     * @param key   Имена переменных(списком)
     * @param value Значения переменных(списком)
     */
    public void putAllParams(List<String> key, List<String> value) {
        IntStream.range(0, key.size()).forEach(i -> this._params.put(key.get(i), value.get(i)));
    }

    /**
     * В общем это пизда
     *
     * @param key   Имя переменной
     * @param value Значение переменное
     */
    public void putParam(String key, String value) {
        _params.put(key, value);
    }

    public URL getUrl() {
        return _url;
    }

    public void setUrl(URL url) {
        this._url = url;
    }

    public String getFullAnswer() {
        return _answer;
    }

    public void setConnectTimeout(int millsec) {
        this._connectTimeout = millsec;
    }

    public void reset() {
        this._connectTimeout = 3000;
        this._url = null;
        this._requestMethod = "POST";
        this._params.clear();
    }

    @Override
    public String toString() {
        return "ManagerWeb{" +
                "_url=" + _url +
                ", _requestMethod='" + _requestMethod + '\'' +
                ", _params=" + _params +
                ", _requestType='" + _requestType + '\'' +
                ", _answer='" + _answer + '\'' +
                ", _connectTimeout=" + _connectTimeout +
                '}';
    }
}
