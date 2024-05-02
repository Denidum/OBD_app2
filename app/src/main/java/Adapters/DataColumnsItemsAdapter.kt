package Adapters

import Table_or_data_classes.Column_to_add_data
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.R
import com.example.obd_app2.interfaces.EditTextChangeListener

class DataColumnsItemsAdapter(var items: List<Column_to_add_data>, var context: Context,  val listener: EditTextChangeListener): RecyclerView.Adapter<DataColumnsItemsAdapter.myViewHolder>(){
    class myViewHolder(view: View, listener: EditTextChangeListener): RecyclerView.ViewHolder(view){
        val columnName: EditText = view.findViewById(R.id.column_name_edittext)
        val dataType: TextView = view.findViewById(R.id.column_data_type_label)
        val numberLabel: TextView = view.findViewById(R.id.number_column_label)
        val columnNameLabel: TextView = view.findViewById(R.id.column_name_label)
        init {
            columnName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        listener.onEditTextChanged(it.toString(), adapterPosition)
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.style_add_data_column_itemlist, parent, false)
        return myViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        holder.numberLabel.text = (position+1).toString()
        holder.columnNameLabel.text = "Column ${items[position].columnName}:"
        holder.dataType.text = "Data type: ${items[position].columnDataType}"
    }


}