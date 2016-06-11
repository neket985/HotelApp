package com.example.nikitos.hotelapp;

import com.google.gson.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getIdFromTitle(JsonArray jsArr, String equalStr){
        String str;
        int i=0;
        for(;i<jsArr.size();++i){
            if (equalStr.equals(jsArr.get(i).getAsJsonObject().get("title").getAsString())){
                break;
            }
        }
        str = jsArr.get(i).getAsJsonObject().get("id").getAsString();
        return str;
    }
    public static String[] getListCities (JsonArray jsArr){
        String [] str = new String[jsArr.size()];
        for(int i=0;i<jsArr.size();++i){
            str[i] = jsArr.get(i).getAsJsonObject().get("title").getAsString();
        }
        return str;
    }

    myTask mt;
    TextView tv;
    ImageView searchbtn;
    EditText search_str;
    Spinner spin_farang;
    Button sendForm;
    TextView dateAt;
    TextView dateTo;
    Context context = MainActivity.this;

    AutoCompleteTextView AutoCompleteCities;
    JsonArray js;
    String mainReq;
    String[] cities;
    int DIALOG_DATE1 = 1;
    int DIALOG_DATE2 = 2;
    int myYear1 = 2016;
    int myMonth1 = 01;
    int myDay1 = 01;
    int myYear2 = 2016;
    int myMonth2 = 01;
    int myDay2 = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mt = new myTask();
        mt.execute();

        AutoCompleteCities = (AutoCompleteTextView) findViewById(R.id.Cities);
        tv = (TextView)findViewById(R.id.tv);
        searchbtn = (ImageView)findViewById(R.id.serchbtn);
        search_str = (EditText)findViewById(R.id.search_string);
        spin_farang = (Spinner)findViewById(R.id.spin_farang);
        sendForm = (Button)findViewById(R.id.send_form);
        dateAt = (TextView)findViewById(R.id.dateAt);
        dateTo = (TextView)findViewById(R.id.dateTo);
        testJsoup();
    }

    private void testJsoup(){
        String inputHtml = "Гостиница \"Алтай\" расположена в экологически чистом, тихом и зеленом районе Москвы. В пяти минутах ходьбы от станции метро \"Владыкино\" находятся четыре корпуса гостиницы. Солнечно-желтый фасад главного корпуса можно увидеть уже при выходе из метро. Для удобства гостей отеля, прибывших на транспорте, имеется платная парковка и въезд на третье транспортное кольцо. Гости Москвы, любящие пешие прогулки, смогут насладиться свежим воздухом и живой природой в Ботаническом саду, который находится в непосредственной близости к главному зданию отеля. Воспользовавшись наземным транспортом, через 5-10 минут можно доехать до музея-усадьбы Шереметьева и телебашни \"Останкино\", увидеть всемирно известный выставочный центр России- ВВЦ ( бывш. ВДНХ). Потратив 15 минут на метро, Вы попадаете в центр Москвы, где можно совершить прогулку по Красной площади, полюбоваться Кремлем, посетить Большой и Малый театры, торговые салоны на Тверской улице. Удобное расположение гостиницы позволяет за 30 минут на транспорте добраться до международного аэропорта \"Шереметьево\". <p>Стоимость бронирования гостиницы \"Алтай\" и цены номеров Вы можете узнать, заполнив форму заявки.</p> <p><a href=\"/catalog/mos/\">Гостиница Москвы</a> \"Алтай\" неоднократно участвовала в отраслевых выставках гостиничного бизнеса. За профессионализм, качество и творческую позицию отель был отмечен дипломами и премиями. <p>По оснащенности, комфортабельности и безопасности \"Алтай\" не уступает самым современным отелям мира. К услугам гостей 665 комфортабельных номеров различных категорий, среди них: 400 номеров туристического класса, 250 улучшенных номеров европейского класса и престижные трехкомнатные апартаменты. В каждом номере созданы все необходимые условия для полноценного отдыха и плодотворной работы. В номерах имеются туалетная комната, ванная или душ, телевизор со спутниковым и кабельным телевидением, холодильник, телефон. Большое внимание уделяется безопасности гостей. Гостиница имеет международный сертификат безопасности. Камеры видеонаблюдения установлены по всему периметру здания, в общественных местах и на этажах номерного фонда. Номера оборудованы электронными замками. Мы рады предложить Вам наши одноместные и двухместные улучшенные номера, которые после реновации полностью соответствуют международным стандартам. А наши трехкомнатные Люкс-апартаменты с оборудованной кухней и кабинетом смогут стать для Вас настоящим домом. <p>В ресторан-клубе гостиницы представлена великолепная кухня разных стран мира, а ассортименту винной карты позавидует любой французский ресторан. Здесь Вы можете провести романтический вечер под сопровождение джазового оркестра, отметить юбилей или свадьбу. Для любителей музыки в ресторане проводятся джазовые концертные вечера с участием российских и зарубежных звезд. В гостинице в круглосуточном режиме работают бар и кафе. В уютном кафе Вы можете заказать завтрак, обед или ужин. В баре послушать по вечерам живую музыку, выпить чашечку ароматного кофе и побаловать себя вкуснейшими кондитерскими изделиями из нашего ресторана. Профессиональные бармены сделают для Вас лучшие коктейли в городе. У любителей спорта есть прекрасная возможность совместить посещение бара с просмотром любимых спортивных программ. Всегда к услугам гостей: <li>Камера хранения и депозитный сейф; <li>Авиа- и железнодорожные кассы; <li>Обмен валюты; <li>Трансфер; <li>Туристическое бюро. <p>Ежедневно для гостей открыт многопрофильный медико-диагностический оздоровительный центр, оснащенный самым новейшим оборудованием. Здесь можно получить квалифицированную консультацию врачей-специалистов, провести комплексное медицинское обследование и лечение на основе современных методик. В уютном салоне красоты и здоровья Вас ждут высококлассные парикмахеры-стилисты, косметологи, мастера маникюра и педикюра, работает финская сауна и турецкая парная, массажный кабинет, фитобар и аптека. <p>Для проведения деловых встреч и мероприятий в гостинице предусмотрены оборудованные современной техникой, конференц-залы, учебные классы и переговорные комнаты. В бизнес-центре можно воспользоваться услугами Интернета, факсимильной связью и другой оргтехникой. Дополнительным, и можно сказать главным, атрибутом любых деловых встреч и переговоров является бизнес-центр. Под этим мы понимаем не только набор услуг, но и отдельное помещение, полностью оборудованное для Вашей плодотворной работы. Поверьте, бизнес-центр отеля не уступает самому современному офису. Бизнес-центр предлагает полный комплекс услуг для деловых людей, включая: <li>Междугороднюю и международную телефонную и факсимильную связь; <li>Аренду компьютеров; <li>Интернет; <li>Печатные работы; <li>Ксерокопирование (черно-белое и цветное); <li>Сканирование; <li>Печать цифровых фотографий. <p>Для проведения деловых встреч и мероприятий различного уровня в гостинице всегда к Вашим услугам: 2 конфренц-зала (50 и 100 мест), 2 учебных класса (30 и 40 мест), комната для переговоров (25 мест) и комната президиума (20 мест). Все залы оснащены современной техникой и оборудованием. Осуществляется организация фуршетов и бизнес-ланчей с доставкой в конференц-залы. На аренду залов предусмотрена гибкая система скидок! Учебные классы как правило арендуется клиентами, проводящими различные тренинги и мастер-классы при ограниченной аудитории (до 50 человек). Салон красоты, расположенный на территории нашего отеля, предлагает Вам на выбор самые модные тенденции косметических и парикмахерских услуг с использованием высококачественных материалов. Опытные косметологи, стилисты по прическам, визажисты подчеркнут Ваш стиль и создадут образ, соответствующий Вашему желанию и внутреннему миру.";
        Document doc = Jsoup.parse(inputHtml);
        String neddedString = "";
        LinkedList<Node> nodes = new LinkedList<>();
        nodes.add(doc.body());
        while (nodes.size() != 0){
            Node node = nodes.removeFirst();
            if (node.childNodes() == null || node.childNodes().size() == 0){
                neddedString = node.toString() + neddedString;
                continue;
            }
            for (Node n : node.childNodes()){
                nodes.addFirst(n);
            }
        }
        Log.i("Парсинг HTML", neddedString);
    }

    class myTask extends AsyncTask <Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mainReq = getHTML("http://h4y.ru:8480/mobile-booking/");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            JsonParser parser = new JsonParser();
            js = parser.parse(mainReq).getAsJsonArray();
            cities = getListCities(js);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, cities);
            AutoCompleteCities.setAdapter(adapter1);

            final String[] searchReq = new String[1];
            final String[] selectNumFarang = new String[1];
            final View.OnClickListener oclSrchImg = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchReq[0] = search_str.getText().toString();
                }
            };
            searchbtn.setOnClickListener(oclSrchImg);
            View.OnClickListener oclFormBtn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cityId = getIdFromTitle(js, AutoCompleteCities.getText().toString());
                    selectNumFarang[0] = spin_farang.getSelectedItem().toString();

                    Intent intent = new Intent(MainActivity.this, search_result.class);
                    intent.putExtra("cityId", cityId);
                    startActivity(intent);
                }
            };
            sendForm.setOnClickListener(oclFormBtn);
        }
    }

//    private void initMyArray() {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                mainReq = getHTML("http://h4y.ru:8480/mobile-booking/");
//                parser = new JsonParser();
//                js = parser.parse(mainReq).getAsJsonArray();
//                cities = getListCities(js);
//            }
//        });
//        thread.start();
//        int i = 0;
//        while (thread.isAlive()){
//            Log.i("Бред, не делай так ниеогда", String.valueOf(i++));
//        }
//        for(JsonElement obj : js){
//            Log.i("ГОРОД", obj.toString());
//        }
//    }

    public void calendar1 (View view){
        showDialog(DIALOG_DATE1);
    }
    public void calendar2 (View view){
        myYear2 = myYear1;
        myMonth2 = myMonth1;
        myDay2 = myDay1;
        showDialog(DIALOG_DATE2);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE1) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack1, myYear1, myMonth1, myDay1);
            return tpd;
        }
        else if (id == DIALOG_DATE2) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack2, myYear2, myMonth2, myDay2);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear1 = year;
            myMonth1 = monthOfYear;
            myDay1 = dayOfMonth;
            dateAt.setText(myDay1 + "." + myMonth1 + "." + myYear1);
        }
    };
    DatePickerDialog.OnDateSetListener myCallBack2 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear2 = year;
            myMonth2 = monthOfYear;
            myDay2 = dayOfMonth;
            dateTo.setText(myDay2 + "." + myMonth2 + "." + myYear2);
        }
    };


/*    protected Dialog onCreateDialog(int id, Bundle args) {
        final int[] choose = {-1};
        final String[] chooseVariant = { "Васька", "Рыжик", "Мурзик" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите способ расселения")
                .setCancelable(false)

                        // добавляем одну кнопку для закрытия диалога
                .setNegativeButton("Назад",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Далее",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if(choose[0] ==-1) {
                                    Toast.makeText(getApplicationContext()
                                            , "Вы ничего не выбрали",
                                            Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Intent intent = new Intent(MainActivity.this, search_result.class);
                                    startActivity(intent);
                                }

                            }
                        })

        // добавляем переключатели
                .setSingleChoiceItems(chooseVariant, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Ваш выбор: "
                                                + chooseVariant[item],
                                        Toast.LENGTH_SHORT).show();
                                choose[0] = item;
                            }
                        });
        return builder.create();
    }*/
}
//Параметры для запроса: "идентификатор горда", "количество мест в номере", "дата заезда", "дата выезда", "Минимальная цена", "Максимальная цена"