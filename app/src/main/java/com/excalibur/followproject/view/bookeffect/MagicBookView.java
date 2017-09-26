package com.excalibur.followproject.view.bookeffect;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.util.logging.Logger;


public class MagicBookView extends FrameLayout {
	public static final boolean DEBUG = true;
	public static final String TAG = "MonthFragment";
	
	private Context mContext;

	private PageContainer mPrePageContainter;
	private PageContainer mCurPageContainter;
	private PageContainer mNextPageContainter;
	private PageContainer mActiveContaiter;

	private Bitmap mPrePageBitmap;
	private Bitmap mCurPageBitmap;
	private Bitmap mNextPageBitmap;
	
	private static final int MOTION_SLOT = 4;
	private static final boolean INTERACT_MODE = true;
	private static final boolean DRAG_MODE = false;
	private boolean mIsInterActMode = true;
	private boolean mIsTurnBack = false;
	
	private float mMotionX = -1;
	private float mMotionY = -1;
	private float mDownX = -1;
	private float mDownY = -1;

	private static final boolean TURN = true;
	private static final boolean RESET = false;
	private boolean mTurnOrReset = RESET;

	private int mCurrentPage = -1;
	private int mPageCount = -1;
	private boolean mIsAnimating = false;

	private DrawingHelper mHelper;

	Scroller mScroller;
	Logger l;
	public MagicBookView(Context context) {
		super(context);
		mContext = context;
	}

	public MagicBookView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public MagicBookView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	private void onTurn(boolean isTurnBack) {
		PageContainer tmp = null;
		int width = MeasureSpec.makeMeasureSpec(mHelper.mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHelper.mHeight, MeasureSpec.EXACTLY);
		if (isTurnBack) {
			tmp = mNextPageContainter;
			mNextPageContainter = mCurPageContainter;
			mCurPageContainter = mPrePageContainter;
			mPrePageContainter = tmp;

			mCurrentPage--;
			mPrePageContainter.setCurPageInBook(mCurrentPage - 1);
			mPrePageContainter.doTurnReload(isTurnBack,mCurrentPage);			
			mPrePageContainter.measure(width,height);
			mPrePageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
			
		} else {
			tmp = mPrePageContainter;
			mPrePageContainter = mCurPageContainter;
			mCurPageContainter = mNextPageContainter;
			mNextPageContainter = tmp;

			mCurrentPage++;
			mNextPageContainter.setCurPageInBook(mCurrentPage + 1);
			mNextPageContainter.doTurnReload(isTurnBack,mCurrentPage);
			mNextPageContainter.measure(width, height);
			mNextPageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
		}
		mActiveContaiter = mCurPageContainter;
	}
	

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mHelper = new DrawingHelper(w,h);
		mHelper.init();
		int width = MeasureSpec.makeMeasureSpec(mHelper.mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHelper.mHeight, MeasureSpec.EXACTLY);
		
		mPrePageContainter.measure(width, height);
		mCurPageContainter.measure(width, height);
		mNextPageContainter.measure(width, height);
		
		mPrePageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
		mCurPageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
		mNextPageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);

		mPrePageBitmap = Bitmap.createBitmap(mHelper.mWidth, mHelper.mHeight,
				Bitmap.Config.ARGB_8888);
		mCurPageBitmap = Bitmap.createBitmap(mHelper.mWidth, mHelper.mHeight,
				Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(mHelper.mWidth, mHelper.mHeight,
				Bitmap.Config.ARGB_8888);

		mHelper.mTouch.x = 0.01f;
		mHelper.mTouch.y = 0.01f;
		
		loadBitmaps();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(!mScroller.isFinished()){
			return false;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		if(mIsInterActMode){
			super.dispatchDraw(canvas);
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(mIsInterActMode){
			return;
		}
		canvas.drawColor(0xFFAAAAAA);
		mHelper.calcPoints();
		if (!mIsTurnBack) {
			mHelper.drawCurrentPageArea(canvas, mCurPageBitmap, mHelper.mPath0);
			mHelper.drawNextPageAreaAndShadow(canvas, mNextPageBitmap);
			mHelper.drawCurrentPageShadow(canvas);
			mHelper.drawCurrentBackArea(canvas, mCurPageBitmap);
		} else {
			mHelper.drawCurrentPageArea(canvas, mPrePageBitmap, mHelper.mPath0);
			mHelper.drawNextPageAreaAndShadow(canvas, mCurPageBitmap);
			mHelper.drawCurrentPageShadow(canvas);
			mHelper.drawCurrentBackArea(canvas, mPrePageBitmap);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			View v = mActiveContaiter.getContent();
			Rect r = new Rect();
			mDownX = mMotionX = ev.getX();
			mDownY = mMotionY = ev.getY();
			
			if(v == null){
				if(canDrag(mDownX,mDownY)){
					changeMode(DRAG_MODE);
				}		
			} else {
				v.getHitRect(r);
				if (!r.contains((int) mMotionX, (int) mMotionY)) {
					if (canDrag(mDownX, mDownY)) {
						changeMode(DRAG_MODE);
					}
				}else{
					return false;
				}
			}
			return true;
		case MotionEvent.ACTION_MOVE:
			float deltaX = ev.getX() - mMotionX;
			float deltaY = ev.getY() - mMotionY;
			float distance = (float) Math.pow(
					(double) (deltaX * deltaX + deltaY * deltaY), 0.5d);
			mMotionX = ev.getX();
			mMotionY = ev.getY();
			if (distance >= MOTION_SLOT) {
				if(!canDrag(mDownX,mDownY)){
					return false;
				}
				mHelper.mTouch.x = ev.getX();
				mHelper.mTouch.y = ev.getY();
				changeMode(DRAG_MODE);
				return true;
			}
			return false;
		default:
			return false;
		}
	}

	private void loadBitmaps() {
			mPrePageBitmap = Util.takeShort(mPrePageContainter, mPrePageBitmap);
			mCurPageBitmap = Util.takeShort(mCurPageContainter, mCurPageBitmap);
			mNextPageBitmap = Util.takeShort(mNextPageContainter, mNextPageBitmap);
	}

	public boolean shouldDragOver() {
		if (mIsTurnBack) {
			mTurnOrReset = RESET;
			return false;
		} else if (mHelper.mTouchToCornerDis > mHelper.mWidth / 2) {
			mTurnOrReset = TURN;
			return true;
		}
		mTurnOrReset = RESET;
		return false;
	}

	private void startAnimation(boolean reset) {
		int dx, dy;
		int duration = -1;
		if (!reset) {
			dx = mHelper.getScrollOverDisPiont().x;
			dy = mHelper.getScrollOverDisPiont().y;
			duration = DrawingHelper.ANIMATE_DURATION;
		} else {
			dx = mHelper.getScrollResetDisPiont().x;
			dy = mHelper.getScrollResetDisPiont().y;
			duration = DrawingHelper.RESET_ANIMATE_DURATION;
		}
		mScroller.startScroll((int) mHelper.mTouch.x, (int) mHelper.mTouch.y, dx, dy, duration);
	}

	public void abortAnimation() {
		if (!mScroller.isFinished()) {
			mScroller.abortAnimation();
		}
	}

	public void computeScroll() {
		super.computeScroll();
		if(mIsInterActMode){
			return;
		}
		if (mScroller.computeScrollOffset()) {
			float x = mScroller.getCurrX();
			float y = mScroller.getCurrY();
			mHelper.mTouch.x = x;
			mHelper.mTouch.y = y;
			postInvalidate();
			if (!mIsAnimating) {
				mIsAnimating = true;
			}
		} else {
			if (mIsAnimating) {
				mIsAnimating = false;
				doTurnIfNeed();
				changeMode(INTERACT_MODE);
			}
		}
	}

	private void doTurnIfNeed() {
		if (mIsTurnBack) {
			if (mCurrentPage <= 0) {
				return;
			}
			onTurn(mIsTurnBack);
		} else if (mTurnOrReset) {
			if (mCurrentPage >= mPageCount - 1) {
				return;
			}
			onTurn(mIsTurnBack);
		}
	}
	
	public void initBookView(int pageCount, int curPage,
							 PageContainer.IPageContainer pre, PageContainer.IPageContainer cur, PageContainer.IPageContainer next) {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		setBackgroundColor(0x00000000);

		mCurrentPage = curPage;
		mPageCount = pageCount;
		mPrePageContainter = new PageContainer(mContext);
		mCurPageContainter = new PageContainer(mContext);
		mNextPageContainter = new PageContainer(mContext);

		mPrePageContainter.setIPageContainter(next);
		mCurPageContainter.setIPageContainter(cur);
		mNextPageContainter.setIPageContainter(pre);
		
		if(mPageCount <= 0){
			return;
		}
		
		if (mCurrentPage < 0 || mCurrentPage >= mPageCount) {
			return;
		}

		mPrePageContainter.setCurPageInBook(mCurrentPage - 1);
		mCurPageContainter.setCurPageInBook(mCurrentPage);
		mNextPageContainter.setCurPageInBook(mCurrentPage + 1);
		
		mPrePageContainter.setPageCount(mPageCount);
		mCurPageContainter.setPageCount(mPageCount);
		mNextPageContainter.setPageCount(mPageCount);
		
		mPrePageContainter.doInit();
		mCurPageContainter.doInit();
		mNextPageContainter.doInit();
		
		mActiveContaiter = mCurPageContainter;
		removeAllViews();
		addView(mActiveContaiter);		
		mScroller = new Scroller(mContext);
	}

	public void setContainterTurnReloadListeners(
			PageContainer.IPageContainer pre,
			PageContainer.IPageContainer next) {
		if (pre != mNextPageContainter.getIPageContainter()) {
			mNextPageContainter.setIPageContainter(pre);
		}

		if (next != mPrePageContainter.getIPageContainter()) {
			mPrePageContainter.setIPageContainter(next);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(mIsInterActMode){
			return false;
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mHelper.mTouch.x = event.getX();
			mHelper.mTouch.y = event.getY();
			if(mHelper.mTouch.y >= mHelper.mHeight){
				mHelper.mTouch.y =  mHelper.mHeight -1;
			}
			this.postInvalidate();
		}else if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if(!canDrag(event.getX(),event.getY())){
				return false;
			}
			mHelper.mTouch.x = event.getX();
			mHelper.mTouch.y = event.getY();
		}else if (event.getAction() == MotionEvent.ACTION_UP) {
			startAnimation(!shouldDragOver());
			this.postInvalidate();
		}
		return true;
	}
	public boolean isFirstPage(){
		if(mCurrentPage == 0){
			return true;
		}
		return false;
	}
	public boolean isLastPage(){
		if(mCurrentPage == mPageCount - 1){
			return true;
		}
		return false;
	}
	private void changeMode(boolean mode){
		if(mode != mIsInterActMode){
			onModeChanged(mode);
			mIsInterActMode = mode;
			
		}
	}
	private void onModeChanged(boolean mode){
		if(mode == DRAG_MODE){
			loadBitmaps();
		}else{
			removeAllViews();
			addView(mActiveContaiter);
		}
	}
	private boolean canDrag(float x,float y){
		if (x < mHelper.mWidth / 3) {
			mIsTurnBack = true;
			if(isFirstPage()){
				return false;
			}
		} else {
			mIsTurnBack = false;
			if(isLastPage()){
				return false;
			}	
		}
		return true;
	}
	public PageContainer getActivePageContainer(){
		return mActiveContaiter;
	}
	public void setBookToPage(int page){
		if(page < 0 || page > mPageCount || page == mCurrentPage){
			return;
		}
		mCurrentPage = page;
		onReInit();
	}
	private void onReInit(){
		int width = MeasureSpec.makeMeasureSpec(mHelper.mWidth, MeasureSpec.EXACTLY);
		int height = MeasureSpec.makeMeasureSpec(mHelper.mHeight, MeasureSpec.EXACTLY);
		
		mPrePageContainter.setCurPageInBook(mCurrentPage - 1);
		mCurPageContainter.setCurPageInBook(mCurrentPage);
		mNextPageContainter.setCurPageInBook(mCurrentPage + 1);
		
		mPrePageContainter.doReInit();
		mCurPageContainter.doReInit();
		mNextPageContainter.doReInit();
		mActiveContaiter = mCurPageContainter;
		
		mPrePageContainter.measure(width, height);
		mPrePageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
		mCurPageContainter.measure(width, height);
		mCurPageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
		mNextPageContainter.measure(width, height);
		mNextPageContainter.layout(0, 0, mHelper.mWidth, mHelper.mHeight);
	}
}
