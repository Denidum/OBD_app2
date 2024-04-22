package Adapters

import Table_or_data_classes.Table
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.obd_app2.R

class TableListItemsAdapter(var items: List<Table>, var context: Context): RecyclerView.Adapter<TableListItemsAdapter.myViewHolder>() {
    class myViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tableName: TextView = view.findViewById(R.id.table_item_name)
        val colList: TextView = view.findViewById(R.id.table_item_col_count_and_list)
        val time: TextView = view.findViewById(R.id.table_item_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.style_table_itemlist, parent, false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.tableName.text = items[position].name
        var strColList: String = items[position].col_count.toString()+" columns: "+items[position].col[0]
        for(i in 1..<items[position].col_count step 1){
            strColList = strColList + ", " + items[position].col[i]
        }
        holder.colList.text = strColList
        holder.time.text = items[position].creation_time
    }
}