package viewutil.mibrainwaveview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.renlei.myapplication.R;

import viewutil.CircleWaveView2;


/**
 * Time 2017/2/23.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class MiBrainAudioRecordView extends RelativeLayout {
    MiBrainCircleWaveView mWaveView;
    ImageView mAudioIV;



    public MiBrainAudioRecordView(Context context) {
        super(context);
    }

    public MiBrainAudioRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MiBrainAudioRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        Miio.d("AudioRecordView","onFinishInflate");
        mWaveView = (MiBrainCircleWaveView) findViewById(R.id.wave_view);
        mAudioIV = (ImageView) findViewById(R.id.audio_record_iv);
    }



    public void enterRecordStatus(){
        mWaveView.enterRecordStatus();
    }

    public void exitRecordStatus(boolean showAnimation){
        mWaveView.exitRecordStatus(showAnimation);
    }

    public void recievedAudio(int radius){
        mWaveView.addCircle(radius);
    }

}
