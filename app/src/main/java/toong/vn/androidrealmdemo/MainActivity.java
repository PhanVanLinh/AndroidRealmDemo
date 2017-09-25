package toong.vn.androidrealmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        deleteAllObject();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButton();
            }
        });
    }

    private void onClickButton() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Log.i("Linh", "count 0 = " + bgRealm.where(Dog.class).count());
                Dog dog = new Dog();
                dog.setName("Rex");
                dog.setAge(1);
                bgRealm.insert(dog);

                Log.i("Linh", "count 1 = " + bgRealm.where(Dog.class).count());

                getCount();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("Linh", "count 3 = " + realm.where(Dog.class).count());
            }
        });
    }

    private void getCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("Linh", "count 2 = " + realm.where(Dog.class).count());
            }
        });
    }

    private void deleteAllObject(){
        realm.beginTransaction();
        realm.where(Dog.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }
}

// Result when click at Button

//09-25 04:34:29.967 30817-31140/toong.vn.androidrealmdemo I/Linh: count 0 = 0
//09-25 04:34:29.967 30817-31140/toong.vn.androidrealmdemo I/Linh: count 1 = 1
//09-25 04:34:29.969 30817-30817/toong.vn.androidrealmdemo I/Linh: count 2 = 0
//09-25 04:34:29.979 30817-30817/toong.vn.androidrealmdemo I/Linh: count 3 = 1

//09-25 04:34:32.497 30817-31214/toong.vn.androidrealmdemo I/Linh: count 0 = 1
//09-25 04:34:32.498 30817-31214/toong.vn.androidrealmdemo I/Linh: count 1 = 2
//09-25 04:34:32.500 30817-30817/toong.vn.androidrealmdemo I/Linh: count 2 = 1
//09-25 04:34:32.508 30817-30817/toong.vn.androidrealmdemo I/Linh: count 3 = 2

//09-25 04:34:48.596 30817-31687/toong.vn.androidrealmdemo I/Linh: count 0 = 2
//09-25 04:34:48.597 30817-31687/toong.vn.androidrealmdemo I/Linh: count 1 = 3
//09-25 04:34:48.599 30817-30817/toong.vn.androidrealmdemo I/Linh: count 2 = 2
//09-25 04:34:48.607 30817-30817/toong.vn.androidrealmdemo I/Linh: count 3 = 3