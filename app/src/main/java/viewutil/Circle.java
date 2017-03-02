package viewutil;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Time 2017/2/23.
 * User renlei
 * Email renlei@xiaomi.com
 */

public class Circle {
    int x;
    int y;
    Paint paint;
    int radius;

    public void resetSizeRadiusAndAlpha(int radius,int alpha,boolean isLast){
        this.radius += radius;
        this.paint.setAlpha(paint.getAlpha()-alpha);
        if (isLast){
            this.paint.setAlpha(0);
        }
    }
    public Circle(int x, int y, Paint paint, int radius) {
        this.x = x;
        this.y = y;
        this.paint = new Paint(paint);
        this.radius = radius;
    }

    public void drawCircle(Canvas canvas){
        canvas.drawCircle(x,y,radius,paint);
    }
}
