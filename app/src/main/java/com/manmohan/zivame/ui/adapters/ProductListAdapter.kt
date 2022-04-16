package com.manmohan.zivame.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manmohan.zivame.R
import com.manmohan.zivame.model.Item
import com.manmohan.zivame.model.Products
import com.manmohan.zivame.utils.Operations

internal class ProductListAdapter(private val product : Products,
                                  private val selectedItem : ArrayList<Item>,
                                  private val listener : OnItemClick)
    : RecyclerView.Adapter<ProductListAdapter.ProductItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ProductItemHolder {
        val inflatedView = parent.inflate(R.layout.item_layout, false)
        return ProductItemHolder(inflatedView)
    }

    //Extension Function to inflate the parent layout
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int,
                                  attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }


    override fun onBindViewHolder(holder: ProductItemHolder,
                                  position: Int) {
        holder.bindData(product.items[position])
    }


    override fun getItemCount(): Int = product.items.size



    internal inner class ProductItemHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        
        private var itemImage : ImageView = itemView.findViewById(R.id.item_image)
        private var itemName : TextView = itemView.findViewById(R.id.item_name)
        private var itemSubHead : TextView = itemView.findViewById(R.id.item_sub_detail)
        private var itemPrice : TextView = itemView.findViewById(R.id.item_price)
        private var itemRating : RatingBar = itemView.findViewById(R.id.item_rating)
        private var addBtn : Button = itemView.findViewById(R.id.add_item)

        fun bindData(item : Item){

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(itemImage)

            itemName.text = item.name.substring(0, Operations.findDifferentiatorPos(item.name))

            itemSubHead.text = item.name.substring(Operations.findDifferentiatorPos(item.name))

            itemPrice.text = "Rs ${item.price}"

            itemRating.rating = itemRating.rating

            if (selectedItem.contains(item))
                addBtn.text = "Remove"
            else
                addBtn.text = "Add"

            addBtn.setOnClickListener {
                if (addBtn.text.equals("Add"))
                     listener.onAdd(item)

                if (addBtn.text.equals("Remove"))
                    listener.onRemove(item)

            }
        }
    }


    interface OnItemClick{
        fun onAdd(item : Item)
        fun onRemove(item : Item)

    }
}