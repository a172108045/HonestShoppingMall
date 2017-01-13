package com.song.honestshoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.song.honestshoppingmall.R;

import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.SELECTED_DOWN_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.SELECTED_DOWN_TO_UP_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.SELECTED_UP_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.SELECTED_UP_T0_DOWN_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.UNSELECTED_DOWN_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.UNSELECTED_UP_DOWN_IMAGE;
import static com.song.honestshoppingmall.view.RadioButtonSort.DrawableRightImage.UNSELECTED_UP_IMAGE;

/**
 * @Creator Administrator
 * @CreatedDate 2017/1/12 22:45
 * @Description ${TODO}
 * @Updater $Author$
 * @UpdateTime $Date$
 * @UpdateDesc ${TODO}
 */

public class RadioButtonSort extends RadioButton {

    /**
     * 是否升序，默认为false,即降序
     */
    private boolean mIsAscendingOrder = false;
    /**
     * 单选按钮选中状态（未选中，降序，升序）
     */
    private Enum_RadioButton_State mEnumRadioButtonState = Enum_RadioButton_State.UNSELECTED;
    /**
     * 单选按钮三角个数类型（只有一个三角显示还是两个三角显示）
     */
    private Enum_RadioButton_Triangle_Type mEnumRadioButtonTriangleType = Enum_RadioButton_Triangle_Type.SINGLE;

    public RadioButtonSort(Context context) {
        super(context);
    }

    public RadioButtonSort(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param imageResourceId 必须是
     */
    public void setDrawableRightImageResource(int imageResourceId) {
        //判断必须是接口中的imageId，如果不是抛出运行时异常
        switch (imageResourceId){
            case UNSELECTED_DOWN_IMAGE:
            case SELECTED_DOWN_IMAGE:

            case UNSELECTED_UP_IMAGE:
            case SELECTED_UP_IMAGE:

            case UNSELECTED_UP_DOWN_IMAGE:
            case SELECTED_UP_T0_DOWN_IMAGE:
            case SELECTED_DOWN_TO_UP_IMAGE:

                //正常执行
                setCompoundDrawablesWithIntrinsicBounds(0, 0, imageResourceId, 0);
                break;

            default:
                throw new RuntimeException("必须是接口中已存在的id，请根据RadioButtonSort.DrawableRightImage.来获取id");

        }

    }

    public void setTextColorByIsChecked(boolean isChecked) {
        if (isChecked) {
            //被选中则设置字体为粉红
            setTextColor(getResources().getColor(R.color.pink));
        } else {
            //没有被选中则设置字体为黑色
            setTextColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    public void toggle() {
        super.toggle();
        if(this.mEnumRadioButtonState == Enum_RadioButton_State.UNSELECTED){
            RadioGroup radioGroup = (RadioGroup) getParent();
            for(int i = 0; i < radioGroup.getChildCount(); i++){
                RadioButtonSort radioButtonSort = (RadioButtonSort) radioGroup.getChildAt(i);
                radioButtonSort.setRadioButtonState(Enum_RadioButton_State.UNSELECTED);
                radioButtonSort.setTextColorByIsChecked(false);
                radioButtonSort.setDrawableRightImageResource(radioButtonSort.getImageResourceId());
            }
        }

        //通过按钮是否被选中设置字体颜色
        setTextColorByIsChecked(isChecked());

        if(isChecked()){
            if(this.mEnumRadioButtonState == Enum_RadioButton_State.UNSELECTED){
                this.mEnumRadioButtonState = Enum_RadioButton_State.SELECTED_DOWN_ORDER;
                this.mIsAscendingOrder = false;
            }else if(this.mEnumRadioButtonState == Enum_RadioButton_State.SELECTED_DOWN_ORDER){
                this.mEnumRadioButtonState = Enum_RadioButton_State.SELECTED_UP_ORDER;
                this.mIsAscendingOrder = false;
            }else if(this.mEnumRadioButtonState == Enum_RadioButton_State.SELECTED_UP_ORDER){
                this.mEnumRadioButtonState = Enum_RadioButton_State.SELECTED_DOWN_ORDER;
                this.mIsAscendingOrder = true;
            }
        }else{
            this.mEnumRadioButtonState = Enum_RadioButton_State.UNSELECTED;
            this.mIsAscendingOrder = false;
        }

        setDrawableRightImageResource(getImageResourceId());
    }

    /**
     * 通过自身成员变量获取需要设置的图片id（需要状态和类型）
     * @return 图标id
     */
    public int getImageResourceId(){
        //默认未选中降序单三角图标显示
        int imageResourceId = UNSELECTED_DOWN_IMAGE;

        //通过state以及isAscendingOrder和triangleType来判断文本应该设置的颜色以及图标的显示（图标的种类以及升序降序两种状态）
        if (mEnumRadioButtonTriangleType == Enum_RadioButton_Triangle_Type.SINGLE) {
            switch (mEnumRadioButtonState) {
                case UNSELECTED:
                    imageResourceId = UNSELECTED_DOWN_IMAGE;
                    break;
                case SELECTED_DOWN_ORDER:
                    imageResourceId = SELECTED_DOWN_IMAGE;
                    break;

                case SELECTED_UP_ORDER:
                    imageResourceId = SELECTED_UP_IMAGE;
                    break;
            }
        } else if (mEnumRadioButtonTriangleType == Enum_RadioButton_Triangle_Type.DOUBLE) {
            switch (mEnumRadioButtonState) {
                case UNSELECTED:
                    imageResourceId = UNSELECTED_UP_DOWN_IMAGE;
                    break;
                case SELECTED_DOWN_ORDER:
                    imageResourceId = SELECTED_UP_T0_DOWN_IMAGE;
                    break;

                case SELECTED_UP_ORDER:
                    imageResourceId = SELECTED_DOWN_TO_UP_IMAGE;
                    break;
            }
        }
        return imageResourceId;
    }


    /**
     * 设置是否升序排序
     *
     * @param isAscendingOrder 是否升序排序
     */
    public void setIsAscendingSort(boolean isAscendingOrder) {
        this.mIsAscendingOrder = isAscendingOrder;
        if(isAscendingOrder){
            this.mEnumRadioButtonState = Enum_RadioButton_State.SELECTED_UP_ORDER;
        }else{
            this.mEnumRadioButtonState = Enum_RadioButton_State.SELECTED_DOWN_ORDER;
        }
    }

    /**
     * 获取是否是升序排序
     *
     * @return 是否升序排序
     */
    public boolean isAscendingSort() {
        return this.mIsAscendingOrder;
    }

    /**
     * 设置单选按钮选择状态
     *
     * @param state
     */
    public void setRadioButtonState(Enum_RadioButton_State state) {
        switch (state) {
            case UNSELECTED:
            case SELECTED_DOWN_ORDER:
            case SELECTED_UP_ORDER:
                this.mEnumRadioButtonState = state;

                break;

            default:
                throw new RuntimeException("状态值不正确，请通过RadioButtonSort.Enum_RadioButton_State.来设置");
        }
    }

    public Enum_RadioButton_State getEnumRadioButtonState(){
        return this.mEnumRadioButtonState;
    }

    public void setTriangle_Type(Enum_RadioButton_Triangle_Type triangle_Type) {
        this.mEnumRadioButtonTriangleType = triangle_Type;
        switch (triangle_Type) {
            case SINGLE:
            case DOUBLE:
                this.mEnumRadioButtonTriangleType = triangle_Type;

                break;

            default:
                throw new RuntimeException("状态值不正确，请通过RadioButtonSort.Enum_RadioButton_Triangle_Type.来设置");
        }
    }

    public Enum_RadioButton_Triangle_Type getEnumRadioButtonTriangleType(){
        return this.mEnumRadioButtonTriangleType;
    }

    //radiobutton右侧三角图片资源id
    interface DrawableRightImage {
        //未选中向下状态单三角的纹理资源
        int UNSELECTED_DOWN_IMAGE = R.mipmap.down_new;
        //选中向下状态单三角的纹理资源
        int SELECTED_DOWN_IMAGE = R.mipmap.down_selected_new;

        //未选中向上状态单三角的纹理资源
        int UNSELECTED_UP_IMAGE = R.mipmap.up_new;
        //选中向上状态单三角的纹理资源
        int SELECTED_UP_IMAGE = R.mipmap.up_selected_new;

        //未选中双三角的纹理资源
        int UNSELECTED_UP_DOWN_IMAGE = R.mipmap.up_down_pink_new;
        //选中向下状态双三角的纹理资源
        int SELECTED_UP_T0_DOWN_IMAGE = R.mipmap.down_pink_new;
        //选中向上状态双三角的纹理资源
        int SELECTED_DOWN_TO_UP_IMAGE = R.mipmap.up_pink_new;
    }

    /**
     * 单选按钮三种状态（未选中，降序，升序）
     */
    public enum Enum_RadioButton_State {
        UNSELECTED,
        SELECTED_DOWN_ORDER,
        SELECTED_UP_ORDER
    }

    /**
     * 三角个数类型（一个三角还是一两个三角）
     */
    public enum Enum_RadioButton_Triangle_Type {
        SINGLE,
        DOUBLE
    }
}
