package viewutil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.renlei.myapplication.R;


/**
 * Time 2017/2/23.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class AudioRecordView extends RelativeLayout {
    CircleWaveView2 mWaveView;
    ImageView mAudioIV;
    public AudioRecordView(Context context) {
        super(context);
    }

    public AudioRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        Miio.d("AudioRecordView","onFinishInflate");
        mWaveView = (CircleWaveView2) findViewById(R.id.wave_view);
        mAudioIV = (ImageView) findViewById(R.id.audio_record_iv);
    }



    public void enterRecordStatus(){
        mWaveView.enterRecordStatus();
    }

    public void exitRecordStatus(boolean showAnimation){
        mWaveView.exitRecordStatus(showAnimation);
    }

    public void recievedAudio(){
        mWaveView.addCircle();
    }

}
