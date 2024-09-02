package com.kes.app029_kt_worldcup

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CountryAdapter(
    private var activity: Activity,
    private var countries: ArrayList<Country>
): BaseAdapter() {

    override fun getCount(): Int = countries.size

    override fun getItem(position: Int): Any = countries[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            view = inflater.inflate(R.layout.list_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val country = countries[position]
        viewHolder.nameView?.text = country.name
        viewHolder.winsView?.text = "Total wins: ${country.wins}"
        viewHolder.flagView?.setImageResource(country.flagSrc)

        view.setOnClickListener() {
            Toast.makeText(activity, country.name, Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private class ViewHolder(view: View?) {
        var nameView: TextView? = view?.findViewById(R.id.item_name)
        var winsView: TextView? = view?.findViewById(R.id.item_wins)
        var flagView: ImageView? = view?.findViewById(R.id.item_image)
    }
}