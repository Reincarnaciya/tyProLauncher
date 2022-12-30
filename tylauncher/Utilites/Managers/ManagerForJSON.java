package tylauncher.Utilites.Managers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/* менеджер по оброботки json.
 * по идеии он должен обрабатывать не только файлы но и запросы
 * (не генерировать и не принимать, просто расшифровать для идеотов(нас))
 */

public class ManagerForJSON {
    private static final String suffix = "[ManagerForJSON] ";

    private char[] _fullText;
    private String[][] _elementEndValue;
    private int _numElement;

    public ManagerForJSON() {
        this._fullText = null;
        this._elementEndValue = null;
        this._numElement = 0;
    }
    // конструктор
    public ManagerForJSON(int i) {
        this._fullText = null;
        this._elementEndValue = new String[i][2];
        this._numElement = i;
    }
    public ManagerForJSON(char[] textFromFile, String[][] elementsValue) {
        this._fullText = textFromFile;
        this._elementEndValue = elementsValue;
        this._numElement = 0;
    }
    // в пизду все возможные пробелы
    public static short SkipSpaces(char[] array, short index) {
        while (index < (array.length - 1) && array[index] == ' ' || array[index] == '\n' || array[index] == '\r' || array[index] == ' ')
            ++index;
        return index;
    }
    public static short AdressLatter(char[] array, char later, short adress) throws Exception {
        while (adress < array.length - 1) {
            if (array[adress] == later) return adress;
            ++adress;
        }
        return adress;
    }
    public char[] GetFullText() {
        return _fullText;
    }
    public String[][] GetElementEndValue() {
        return _elementEndValue;
    }
    public String GetOfIndex(int i, int j) {
        return _elementEndValue[i][j];
    }
    public void setOfIndex(String str, int i, int j) {
        _elementEndValue[i][j] = str;
    }
    public void CrateMem(int numElm) {
        _elementEndValue = new String[numElm][2];
        this._numElement = numElm + 1;
    }
    // читать данные с файла
    public void ReadJSONFile(String pathToFile) throws Exception {
        File file = new File(pathToFile);
        //_numElement = numElement;
        if (!file.exists()) throw new Exception("Файл не найден");
        try (FileReader fileReader = new FileReader(file)) {
            int later;
            _fullText = new char[((int) file.length())];
            for (int i = 0; (later = fileReader.read()) != -1; ++i) {
                _fullText[i] = (char) later;
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
        if (_fullText[0] != '{' || _fullText[(int) file.length() - 1] != '}')
            throw new Exception(suffix + "файл не джсон");
        String[] arrStr = String.valueOf(_fullText).replaceAll("[ {}\"'\n\r ]", "").split("[:,]");
        System.err.println(Arrays.toString(arrStr));
        _numElement = arrStr.length >> 1;
        _elementEndValue = new String[_numElement][2];
        for (short i = 0; i < arrStr.length; ++i) {
            if (i % 2 != 0) _elementEndValue[i >> 1][1] = arrStr[i];
            else _elementEndValue[i >> 1][0] = arrStr[i];
        }
        //_elementEndValue = new String[numElement][2];
    }
    public void WritingFile(String path) throws Exception {
        if (_elementEndValue.length < 1) {
            throw new Exception(suffix + "Массив не объявлен");
        }
        StringBuilder str = new StringBuilder();
        short i = 0;
        str.append("{\n");
        do {
            str.append(String.format("    \"%s\":\"%s\"", _elementEndValue[i][0], _elementEndValue[i][1]));
            if (++i < _elementEndValue.length) str.append(",\n");
            else str.append("\n}");
        } while (i < _elementEndValue.length);
        File file = new File(path);
        try (FileWriter FileWriter = new FileWriter(file)) {
            FileWriter.write(str.toString());
            FileWriter.flush();
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}