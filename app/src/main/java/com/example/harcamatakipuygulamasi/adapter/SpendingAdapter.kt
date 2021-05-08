package com.example.harcamatakipuygulamasi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.harcamatakipuygulamasi.R
import com.example.harcamatakipuygulamasi.database.Spending
import com.example.harcamatakipuygulamasi.databinding.CardViewBinding

class SpendingAdapter(
    private val clickTracker: SpendingClickTracker,
    private val changeTracker: TypeOfMoneyTracker,
    private var type: Int,
    private var rate: Float
): ListAdapter<Spending, ViewHolder>(SpendingCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.upRate(type, rate)
        holder.connect(item, clickTracker)
    }

    fun changeTypeOfMoney (type: Int, rate: Float) {
        this.type = type
        this.rate = rate
        notifyDataSetChanged()
        var total = 0f
        this.currentList.forEach {
            total += it.theMoneySpent
        }
        changeTracker.onChange(total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, type, rate)
    }
}

class ViewHolder private constructor(
    private val binding: CardViewBinding,
    private var type: Int,
    private var rate: Float
): RecyclerView.ViewHolder(binding.root) {

    fun upRate(type: Int, rate: Float){
        this.type = type
        this.rate = rate
    }

    fun connect(item: Spending, clickTracker: SpendingClickTracker) {

        binding.imageViewCardView.setImageResource(when(item.typeOfSpending) {
            1 -> R.drawable.bill
            2 -> R.drawable.home
            else -> R.drawable.bags_other
        })

        binding.textViewCardViewDescription.text = item.descriptionOfSpending
        val spendingMoney = "%.0f".format(item.theMoneySpent * rate)
        val textSpendingMoney = when(type) {
            1 -> "$spendingMoney €"
            2 -> "$spendingMoney $"
            3 -> "$spendingMoney £"
            else -> "$spendingMoney ₺"
        }
        binding.textViewCardViewMoney.text = textSpendingMoney
        binding.cardViewCardView.setOnClickListener {
            clickTracker.onClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup, type: Int, rate: Float): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CardViewBinding.inflate(inflater, parent, false)
            return ViewHolder(binding, type, rate)
        }
    }
}

class SpendingClickTracker(val clickTracker: (spending: Spending) -> Unit) {
    fun onClick(spending: Spending) = clickTracker(spending)
}

class TypeOfMoneyTracker(val tracker: (total: Float) -> Unit) {
    fun onChange(total: Float) = tracker(total)
}

class SpendingCallBack : DiffUtil.ItemCallback<Spending>() {
    override fun areItemsTheSame(oldItem: Spending, newItem: Spending): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Spending, newItem: Spending): Boolean {
        return oldItem == newItem
    }
}