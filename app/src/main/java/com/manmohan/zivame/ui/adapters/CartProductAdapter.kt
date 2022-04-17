package com.manmohan.zivame.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manmohan.zivame.R
import com.manmohan.zivame.model.Item

import com.manmohan.zivame.utils.Operations

internal class CartProductAdapter(private val selectedItem: ArrayList<Item>,
                                  private val listener : CartItemAction)
    : RecyclerView.Adapter<CartProductAdapter.SelectedItemHolder>() {

    private val rupeesSymbol = "Rs "

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SelectedItemHolder {
        val inflatedView = parent.inflate(R.layout.selected_item_layout, false)
        return SelectedItemHolder(inflatedView)
    }

    //Extension Function to inflate the parent layout
    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int,
                                  attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun getTotalAmount() : Int {
        var amount = 0
        selectedItem.forEach {
            amount += Integer.parseInt(it.price) * it.qty
        }
        return amount
    }


    override fun onBindViewHolder(holder: SelectedItemHolder,
                                  position: Int) {
        holder.bindData(selectedItem[position])
    }


    override fun getItemCount(): Int = selectedItem.size


   internal inner class SelectedItemHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

       private var itemImage : ImageView = itemView.findViewById(R.id.item_image)
       private var itemName : TextView = itemView.findViewById(R.id.item_name)
       private var itemSubHead : TextView = itemView.findViewById(R.id.item_sub_detail)
       private var itemPrice : TextView = itemView.findViewById(R.id.item_price)
       private var qtyInc :  ImageView = itemView.findViewById(R.id.qty_increase)
       private var qtyDec : ImageView = itemView.findViewById(R.id.qty_decrease)
       private var qty : TextView = itemView.findViewById(R.id.qty_value)

        fun bindData(item : Item){

            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(itemImage)

            itemName.text = item.name.substring(0, Operations.findDifferentiatorPos(item.name))

            itemSubHead.text = item.name.substring(Operations.findDifferentiatorPos(item.name))

            itemPrice.text = rupeesSymbol.plus(item.price)

            qty.text = item.qty.toString()

            qtyInc.setOnClickListener {
                modifyQuantity(item, 1)
                qty.text = item.qty.toString()
                listener.onAmountModified(getTotalAmount())
            }

            qtyDec.setOnClickListener {
                modifyQuantity(item , -1)
                qty.text = item.qty.toString()
                listener.onAmountModified(getTotalAmount())
            }
        }

       private fun modifyQuantity(item: Item, modifier : Int) {
          if (modifier == 1 && item.qty <= 9)
              item.qty += modifier
           else if (modifier == -1 && item.qty > 1)
              item.qty += modifier
           else
              Toast.makeText(itemView.context,
                  "Maximum of 10 and Minimum of 1 only",
                  Toast.LENGTH_SHORT).show()
       }
   }

    interface CartItemAction {
        fun onAmountModified(amount : Int)
    }

}