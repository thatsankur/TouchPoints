package ankur.in.touchpoint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by ankur on 11/12/15.
 */
public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        DrawPanel drawingPanel = new DrawPanel(getApplicationContext());
        rl.addView(drawingPanel);

    }

    public class DrawPanel extends View {
        private Paint paint;
        private Paint paint1;
        private Paint paint2;
        // Canvas c;
        MainActivity m1 = new MainActivity();
        int color = 0;
        @SuppressWarnings("rawtypes")
        private ArrayList points;

        @SuppressWarnings("rawtypes")
        private ArrayList strokes;

        private ArrayList<Point> mCircles = new ArrayList<>();

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
            this.setMeasuredDimension(parentWidth, parentHeight);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            initCircleCord(parentHeight, parentWidth);
        }

        @SuppressWarnings("rawtypes")
        public DrawPanel(Context context) {
            super(context);

            points = new ArrayList();
            // points1 = new ArrayList();
            // points2 = new ArrayList();
            strokes = new ArrayList();
            paint = createPaint(Color.BLUE, 11);
            paint1 = createPaint(Color.GREEN, 11);
            paint2 = createPaint(Color.RED, 11);


        }

        private void initCircleCord(int height, int width) {
            mCircles.clear();
            for(int j = 20 ;j<width;j+=width/20) {
                for (int i = 20; i < height; i += height / 20) {
                    Point p = new Point(j, i);
                    mCircles.add(p);
                }
            }
        }

        @SuppressWarnings("rawtypes")
        public void onDraw(Canvas c) {
            super.onDraw(c);

            this.setBackgroundColor(Color.WHITE);
           /* for (Object obj : strokes) {
                drawStroke((ArrayList) obj, c, color);
            }*/
            drawCircles(c);
            drawStroke(points, c, color);
            color = 0;

        }

        private void drawCircles(Canvas c) {
            if (mCircles.size() > 0 ) {
                for (int i = 0; i < mCircles.size(); i++) {
                    Point p=  mCircles.get(i);
                    c.drawCircle(p.x, p.y,2, paint2);
                }
            }
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public boolean onTouchEvent(MotionEvent event) {


            if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                points.add(new Point((int) event.getX(), (int) event.getY()));
                color = getColor();
                invalidate();
            }

            if (event.getActionMasked() == MotionEvent.ACTION_UP) {

                this.strokes.add(points);
                points = new ArrayList();
                invalidate();
            }
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {

               /* points.add(new Point((int) event.getX(), (int) event.getY()));
                color = getColor();
                invalidate();*/
            }
            return true;
        }

        public int getColor() {
            return color;
        }

        private void drawStroke(@SuppressWarnings("rawtypes") ArrayList stroke,
                                Canvas c, int i1) {

            if (stroke.size() > 0 && i1 == 0) {
                Point p0 = (Point) stroke.get(0);
                for (int i = 1; i < stroke.size(); i++) {
                    Point p1 = (Point) stroke.get(i);
                    c.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
                    p0 = p1;
                }
            } else if (stroke.size() > 0 && i1 == 1) {
                Point p0 = (Point) stroke.get(0);
                for (int i = 1; i < stroke.size(); i++) {
                    Point p1 = (Point) stroke.get(i);
                    c.drawLine(p0.x, p0.y, p1.x, p1.y, paint1);
                    p0 = p1;
                }
            } else if (stroke.size() > 0 && i1 == 2) {
                Point p0 = (Point) stroke.get(0);
                for (int i = 1; i < stroke.size(); i++) {
                    Point p1 = (Point) stroke.get(i);
                    c.drawLine(p0.x, p0.y, p1.x, p1.y, paint2);
                    p0 = p1;
                }
            }

        }


        private Paint createPaint(int color, float width) {
            Paint temp = new Paint();
            temp.setStyle(Paint.Style.STROKE);
            temp.setAntiAlias(true);
            temp.setColor(color);
            temp.setStrokeWidth(width);
            temp.setStrokeCap(Paint.Cap.ROUND);

            return temp;
        }

    }
}