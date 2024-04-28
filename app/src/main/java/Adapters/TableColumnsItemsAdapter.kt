package Adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
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

class TableColumnsItemsAdapter(var items: List<Int>, var context: Context, private val listener: EditTextChangeListener): RecyclerView.Adapter<TableColumnsItemsAdapter.myViewHolder>(){
    class myViewHolder(view: View, listener: EditTextChangeListener): RecyclerView.ViewHolder(view){
        val columnName: EditText = view.findViewById(R.id.column_name_edittext)
        val dataType: Spinner = view.findViewById(R.id.column_data_type_spinner)
        val dataGroup: LinearLayout = view.findViewById(R.id.table_data_layout)
        val numberLabel: TextView = view.findViewById(R.id.number_column_label)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.style_add_delete_column_to_table_itemlist, parent, false)
        return myViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.numberLabel.text = (items[position]+1).toString()
        val dataTypeArray = arrayListOf("Int", "Text")
        val adapter = ArrayAdapter(context, R.layout.style_spinner_data_type_selected_item, dataTypeArray)
        adapter.setDropDownViewResource(R.layout.style_spinner_data_type_item)
        holder.dataType.adapter = adapter
        holder.dataType.onItemSelectedListener= object: OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                index: Int,
                id: Long
            ) {
                listener.onItemSelected(holder.dataType.selectedItem.toString(),holder.adapterPosition)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


}