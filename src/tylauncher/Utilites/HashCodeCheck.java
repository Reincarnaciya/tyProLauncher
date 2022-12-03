package tylauncher.Utilites;

import tylauncher.Main;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class HashCodeCheck {
    private static String hash = "";


    public static void Hash() throws IOException, NoSuchAlgorithmException {
        hash = "";
        File dirClient = new File(Main.getClientDir() + File.separator + "TySci_1.16.5");
        if(!dirClient.exists()) return;

        File[] files;
        files = dirClient.listFiles();
        for (File file : files) {
            switch (file.getName()) {
                case "runtime":
                case "versions":
                    listFilesForFolder(file);
                    break;
            }
        }
    }
    private static void listFilesForFolder(final File folder) throws IOException, NoSuchAlgorithmException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                hash += checksum(fileEntry.getAbsolutePath());
            }
        }
    }
    public static String checksum(String fileName) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(fileName);

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        fis.close();
        return sb.toString();
    }

    public static String getHash() throws IOException, NoSuchAlgorithmException {
        Hash();
        return hash;
    }

    public static boolean CheckHashWithServer() {
        String line = "432cddcb487ffb999a738d251c841191d2091a567e834c4a2eb952de168e977d5b2038c4aa4430561d07d0328324c717191d85b926247a8aff45ee30808cd61471c240bfb4825ffbaa762439f5a6f99fdde10ec36be410229d7af47a2bcecdf9fa737f0eb9b5915d681affcad6a5e28bcb63e262f0850bd8c3e282d6cd5493db9c4fa6c85a740e223ee3182fd8488b5ab1afa5ba110d55ea89e59cda9ea22d0694f6be7c545cc08032b6d9cdecc51ccb84a04b93034abab9d3f1bf9579c0573d0a248b2be7ae671c4c9f2b8b9abbe9189d16601f2b98eb7cec5ecaf44664850d133c6c027522dc5d0aa8c6c32a151259907ea1847d6a585884f750818d7e9b4df55671e307bc086de1decc97200a5a0ad3bb1dbdcbc9a380797a908c3f2d7216b1e0fe3d691d595c9ddad9623599386ac3b0b352d63b2b86b0e717a95026d400649632ac140ac3400ca72413ba90e8261a496db0e43e0fe366c7286314b65e052520e902aedd041f580ea629bc48ae6131401e170ddd8437635c4c8571a80341f3e13221f477836ba6a7bdfe656f57497a691b501044d545ca3444e7a3476d7ba3a2881dff896f65bdf0672a1664c4d4f49218872d803801934638f44274000d62b6858186ce0dcfcd9b685d4090607c271c881d2ffeb455a478d2376ca34c9e7c561ea070de7d1d9d1aa236ebe4a2a27b23b0aab68e65b93bb6477f059995745ed6faed0b5fe8a02bb78c93c422f948a8857dad57402f03cb0f3e9a5eca2fbe249a2b3acce5b62086cb0c1c949c488994dbe8e68afc4535ba555b000a14e3944c5058699eb4f437f7ce6c0a75e88199856e7bdeb0f766723f927c71427875b9f839a5b9994ea8648b3e66c833de7d81b14a883241cab264cfeb29c5d0ae5ba5a8215d3539ff54b3abbc304fa51ba3b4ede8a32e77975127f2f010c14d5d0153c5767eae1e5fabaf111456b92969487ca13967cea5f949a3a50cd58c91a28d45d22b8fd4273cb2870d6359487d8870366df8f8634c7e5c0b119b71a40d2acc00ddb7b5da3cdad8606e4e840883d6e7a543ca3385921eaaa797735fc3d57a195f8440e22eed0b0d23c6448b7462a3f5b13044662faef0cbfdb1799fa2eaf5efd6b08b8d197bb0e81474e3ae571c82338ee90da043972903bbbc17c0266cbdcb2c9cbd0fcba87760b7f7ff1ea4581a49975d94deea8e16c6e00c45fc4188dab20968e5afc53b78733b46e3bea978f164bf79dbdf92c328e1f4ecd2ece82c93264daaa5d109c6a3970f71392b5e42bad6ca4601eee1e2f4b9aef8f941a1d05b79d7df3ca8d16bded6a54977b30e66864d3398751c8908360f86d7f0655bd90bc0f26d0892ff3079db69326c9a7149e3d252a92a8d3601cd8980da444f2bb006e87b0db1572f1b53adb54925aea8a1ec34f01f55ecf63f67f9672f116cb111e08bd5df3ca8d16bded6a54977b30e66864d33c5bb20c40021c10b74525c7f88d70dde86cac781c7d94a42bb76e43592846f43c4770d6b1b41150085a73605fc9069edbf01fd179b69258bfc1253363d87016da4fd14c13d0f6edb35d9a64c0bb1fe237dd216a6c8c7daec52a815bc9bb962b76e5fbfedc2841a62fc6b7ba9927eeed16630c51a361d0ba5ecaa99054ee9ac0b5b8a3605fda84b05b087c04db11bcfc22894ece7b8de355b13978d6b8ec6e68cb3174769a9e9e654812315468ae9c5fa9ec10e4a9aefd1e14ada56d757dda0d53b22ca5da5b9a454044c1eac84f938a9e8611000c5d1ef0143066196187ce5c038a702e0aa0b2a7a2165747ed672fa7b7c7f0b7a9fbd02a03689eedcb8b686346cd89cf0905db3c543ef818a379530841077883868cb42ff899a085d0f9487698b3735d28a8d712781e513c02026593bcac99fd0b0aaaf8c0e294006d69383fe5b071854133d3eb6848a301a2a75c9b25f317dc17d83fd8d80df4eee1a6f1024822bc5c020c19eb8af9288a874c875a251e8850c0ea1bf609100b1f37a4e680b0b684e3038a662dae061211a4d87e15ad027f8fd7d74aff3bf8cbddba3aa04e451f72c3c2569e1174a83a294f7c082d69e5e954bc0e625a69a0a430e80dcf724499f2a4e0a25a41c1ff80df2d073e4fd40a6f317d17705b4d0241f4ebb45962d97f51a4fd734d6c657e512eda613912351531cbbe256939e7ab12fcc256fbf3a10f23396e21454e6bdfb0db2d124db851002f18fc4916f83e0fc7e33dcc1fa09a387b65159c9887265babdef9ca8dae524b9dee2469f9cc8ec39d5bdb39015001d3fda2edb4a89ab60a23c5f7c7d81ddf507712b379fdc5a8d539811faf51d02d072fb69e4c180d6704a9da8ff64772eab6c441cd302e2eb77fbfdb2f3b4590c811bafa6f97801186910e9b1d9927fe2d77c3b5274b8161328ab5c78f66dd0d06d32848bd173b9444b71922616e0645ec11ab66fede3042ee75dfd19032c8a72a81c4b0f3bf9a499429e14a881010ef6b7279f1c3ba0b63806f37f6b9d33c314d52d6766cd66f3967127b219e776c7b19bb1253a3f79152ea273cf6a52a18080a6005be45c88900a15bc80d461b60c30e6f84c081895acdfd98da0f496e1dd3d880baacb176553deab39edbe4b74380d880baacb176553deab39edbe4b74380d249053609eaf5b17ddd42149fc24c469cb81fed291361d1dd745202659857b1bc29d0c4fcb4946d6676e5faac3c7c64cc29c5ce0afb0c7dc8eae4fe4920f0b92ac5eb85f5063095db364508d2c193b8cafe6e1a4176d639129449121e45de3fbfa6246ba92a7e2474addf23ce1777b2ae5aab5dc1407d1fb64c7628be2906d59944c00ed481660573088d19ed870105a005faac2118450bfcd46ae414da5f0e5d1b2a627fe8f79cf1fb3d2fefc58f8ad65e841cbff7777c462c064a105cd669370049626a96986f398f823ec19bc8058352d3349bc9293814990a1579062c57560e972a0420e4bd8a23c18cdb52eec41577bff07383c779a991810fba874958bd8b47b11e300ef3e8be3e6e50ac6910be0e5428560288e685dbffc0d2776d4a61c2ffea868138a14fcf8ffcc375a0ab1af0c5c24ef340aea5ccac002177e5c09793ae1ab32085c8de36541bb6b30da7c4d666869c97cdb9e1381a393ffe50a3a630a6fa16c414f3de6110e46717aad535dd099908b722236aa0c0047c56e5af2b75309b925371b38997df1b25c1ea508a0c96aa334f1aeaa799773db3e6cba9cc1397e8d6e6abcd727c71fca2132e2181eddfb1ee252055556f40cdc79632e98269d03935907969c3f11d43fef252ef11e9d8f133a442da6b0c74d49bc84a34189cdf623e11aaf0407328fd3ada32c071e9d8f133a442da6b0c74d49bc84a341694a59efde0648f49fa448a46c4d89481e9d8f133a442da6b0c74d49bc84a341cc8dd9ab7ddf6efa2f3b8bcfa31115c01e9d8f133a442da6b0c74d49bc84a341e57fce4e57f292bfae09c6de315d0b03e9f747160dbc819c4bcb9c6eb13c5350df21aa9a2da9f94763bdcc80f07c9afd971683e69ca9cc831afec282e999517c0876bcedfd8e60815378359f5a428f3eefa3ad7225fb79074246e8911e47326468704dafee92b51b1ee8c12230298d8154500f89d093b141a3b2f1355ca3315cc677ff69e70dc36a67c72a3d7ef84d28809c50033f825eff7fc70419aaf30317f63bea1f4a31317f6f061d83215594df7b46c291e7073c31d3ce0adae2f7554f81a43119ab15099c1d70e2d683fc8c0a71a7de7dbe2977f6ece75c904d430b62aa2ad1e76bd7841fd9fdc4f5990892e491aa6ea7320140f30379f758d626e59d19a5c7f5186854362281a152e756ce2faa2eaf41de613962cb467d37e8f16fb57c5514b805b4a954bc55d67b44330c69f8734590a1aec97f6b22f08d1ad1b4bbd00b062395ebbcc4269c4e1fba474d11501a694c2900df997c6813fc3dc29abeb2c6eae6382150192ea391239374718091c7195d1abf0081758ce00c8248732cb40b81544993ba86a858d579a06b3ef211340cd598a8517a0fd315a319716a08b7aba3dfea0468195be1256c959135e69107d028bd329dbfe4c1f19015ed6d8057aaaa3176dc28fc554ef0906d01041ad41d8cd98f00b204e9800998ecf8427eee4ed9c75a1aaa04dfd192382c57900c4f95242740bfb7b133b879597947a41e15587f96261d342f4195b7b5308e415a7d4abbcfb06d083f349e27d7e6972f3c98f46ab6481d87c4d77e0e91a6dbc15f0f1123976b959ac5e8b89eb8c245c4bd80c849b03d8457346027b4da21d77958ab9db8d553033c0326bd2d38d77f84c161d2b0ca27981f86ec901d528e9a26bd55fb6ecfb9c81819a76e8d91d83dfc6b075f6c8173a2bd6e025fc3ef7754defca57b9157ff3bb308208e79ef9e19187e0d044d08a753c69d4c08a449f3db06ca532f9686b0b55b3d7cf9f6733f29ba289d58a11052847be02502653b9fb9c9bbf6d4f19a41f245991382bbec2b7879195c7b7a7e9ceedd779ddf531ea58db8e99d58a11052847be02502653b9fb9c9bbea86d55e20e0f21818750c211f45339ccdcaa2d4874a0aaab526c52e1fff2fea6f4c13fe58b839ee7f057c0213688d38c75adb723dc1e8da6882e971de1a409b93aeb5ec9f94134784373f370d295a616f4c13fe58b839ee7f057c0213688d38fbbe79cdea2217e7472b685833415781310adc26c92b020fb6d2944092d81312e3140dc7f0126f76158089d28efe8d904f7a6367c6c42a8a1ee061f44ba0684db8ea778d75b1150ec0eec59d764e57cde3140dc7f0126f76158089d28efe8d90584f81d398eba50d1154fc339fbff717780ed18868c28c0c249379982ea3297a6ee0f89c37c3fddc254fdb8c7bdb28b068b37c18052fb770e77477e1e53a342818d5a928ba6fa486f936cf3807a8668c236817b9ba4f101e25518f1158b7691f3c0e66f2ec6b7185e3ef60115b79c3b5a0e616c8b75575f45497864d650005ecf9fe876da47aa5278e9d5a43672fbd4f5dc7452c51330beb7a178d7093cdac49d1a6d5194c129c008dc8fdb9ff1a6c6aae277b62653af1bdbb27b73ea98970bb38d57c396ac882a59d99a80e984bb5e35b9e16f4c69fbab3286972e2c3e57f5151941725f5769914e6c02efcff31f95109a3038739628fb091f1be90c2903fb7a21338306c8027ebc459c57db8459777aab7d521c776b290c5eb725832d0c2b5b1b872f6e2250ca8e24beb0cfafa62bcbea36e6601b1b9c5dc85eb66cb438887aab7d521c776b290c5eb725832d0c2b56eef8babb8dd2c3ab1940937aa387ad6214a0bc5ae5882495d94f7779d64b3233d47e750e4ec109d441a427ab8b3761429f4cbe5b7a8e2398582d435a171e35b";
        try {
            if(line.equals(getHash())) return true;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }


        /*
        try {

            InputStreamReader isR;
            BufferedReader bfR;

            URL url = new URL("");//ссылка на getHash с сервера
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            Map<String, String> arguments = new HashMap<>();
            arguments.put("login", "");
            arguments.put("password", "");
            arguments.put("email", "");
            arguments.put("repeat_password", "");
            StringJoiner sj = new StringJoiner("&");
            for (Map.Entry<String, String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);

            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
                if (HttpURLConnection.HTTP_OK == http.getResponseCode()) {
                    isR = new InputStreamReader(http.getInputStream());
                    bfR = new BufferedReader(isR);
                    String line;

                    line = bfR.readLine();




                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
        return false;
    }
}
