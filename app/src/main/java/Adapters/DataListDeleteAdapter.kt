package Adapters

import Table_or_data_classes.Data_list_row
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.CompoundButtonCompat
import com.example.obd_app2.R
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete

class DataListDeleteAdapter(private val context: Context, private val items: ArrayList<Data_list_row>, private val listener: ChooseTableOrDataToDelete) :
    ArrayAdapter<Data_list_row>(context, R.layout.style_data_view_delete_itemlist, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val isChecked = items[position].isChecked
        val columnNames = items[position].columnData
        val columnCount = items[position].columnCount
        val isHeader = items[position].isHeader

        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.style_data_view_delete_itemlist, parent, false)

        val checkBox = view.findViewById<CheckBox>(R.id.data_item_check_box)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
        val textViewList = arrayListOf<TextView>(
            view.findViewById(R.id.col1),view.findViewById(R.id.col2),view.findViewById(R.id.col3),
            view.findViewById(R.id.col4),view.findViewById(R.id.col5)
        )
        val dividerViewList = arrayListOf<View>(
            view.findViewById(R.id.divider_1),view.findViewById(R.id.divider_2),view.findViewById(R.id.divider_3),
            view.findViewById(R.id.divider_4),view.findViewById(R.id.divider_5)
        )

        for(i in 0..<(5-columnCount) step 1){
            dividerViewList[4-i].visibility = View.GONE
            textViewList[4-i].visibility = View.GONE
        }
        for(i in 0..<columnCount step 1){
            textViewList[i].text = columnNames[i]
        }

        if(isHeader){
            layout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
            val blackColor = ContextCompat.getColor(context, R.color.almostblack)

            for(i in 0..<columnCount step 1){
                textViewList[i].setTextColor(blackColor)
                dividerViewList[i].setBackgroundColor(blackColor)
            }
            checkBox.visibility = View.INVISIBLE
            val font = ResourcesCompat.getFont(context, R.font.inter_bold)
            for(i in 0..<columnCount step 1){
                textViewList[i].typeface = font
            }
        }
        else{
            checkBox.setOnClickListener{
                if(checkBox.isChecked){
                    listener.buttonChecked(position)
                    items[position].isChecked = true
                }
                else{
                    listener.buttonUnchecked(position)
                    items[position].isChecked = false
                }
            }
            checkBox.isChecked = isChecked
        }

        return view
    }
}