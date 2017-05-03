package viewutil.mibrainwaveview;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.renlei.myapplication.R;

import viewutil.AudioRecordView;

/**
 * Time 2017/2/22.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class TestCircleViewActivity extends Activity {
    //    CircleWaveView2 mCircleWaveView;
    private AudioRecordView mAudioRecordView;
    Button button;
    MiBrainWaveView miBrainWaveView;
    MiBrainAudioRecordView miBrainAudioRecordView;
    TextView mTextTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_waveview);

//        mCircleWaveView = (CircleWaveView2)findViewById(R.id.circleWaveView);
        mAudioRecordView = (AudioRecordView) findViewById(R.id.wave);
        miBrainAudioRecordView = (MiBrainAudioRecordView) findViewById(R.id.wave2) ;
        mTextTV = (TextView) findViewById(R.id.test_tv);
        mTextTV.setText(getName("1/啦啦啦"));
        button = (Button) findViewById(R.id.receive_wave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    mCircleWaveView.addCircle();
                mAudioRecordView.recievedAudio();
                receiveVolume();
            }
        });
        findViewById(R.id.stop_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCircleWaveView.exitRecordStatus(true);
                mAudioRecordView.exitRecordStatus(true);
            }
        });
        findViewById(R.id.start_wave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCircleWaveView.enterRecordStatus();
                mAudioRecordView.enterRecordStatus();
            }
        });


        miBrainWaveView = (MiBrainWaveView) findViewById(R.id.mi_brain_wave_view);

        miBrainWaveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                miBrainWaveView.setRadius(miBrainWaveView.dip2px(52));
            }
        }, 200);

    }


    private void receiveVolume() {
        double ran = Math.random() * 20 + 40;
        miBrainWaveView.receiveVoice(ran);
//        miBrainAudioRecordView.recievedAudio(((int)miBrainWaveView.getRadiusEnd()/3+miBrainWaveView.dip2px(5)));

        miBrainWaveView.postDelayed(new Runnable() {
            @Override
            public void run() {
                miBrainAudioRecordView.recievedAudio(((int)miBrainWaveView.getRadiusEnd()/2+miBrainWaveView.dip2px(5)));
            }
        },50);
    }

    private SpannableString getName(String s){
        if (TextUtils.isEmpty(s) || s.length()<=2)
            return null;
        SpannableString spannableString = new SpannableString(s);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gateway_green)),0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.mi_brain_circle_view_green)),2,s.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(new AbsoluteSizeSpan(70),0,1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(45),1,s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
