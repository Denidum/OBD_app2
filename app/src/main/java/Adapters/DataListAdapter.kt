package Adapters

import Table_or_data_classes.Data_from_row
import Table_or_data_classes.Data_list_row
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.obd_app2.R
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete

class DataListAdapter(private val context: Context, private val items: ArrayList<Data_from_row>):
    ArrayAdapter<Data_from_row>(context, R.layout.style_scan_data_itemlist, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.style_scan_data_itemlist, parent, false)

        val colName = items[position].name
        val colData = items[position].data

        val colNumText = view.findViewById<TextView>(R.id.number_column_label)
        val colNameText = view.findViewById<TextView>(R.id.column_name_label)
        val colDataText = view.findViewById<TextView>(R.id.column_data)

        colNumText.text = "${position+1}"
        colNameText.text = "${colName}: "
        colDataText.text = colData

        return view
    }
}