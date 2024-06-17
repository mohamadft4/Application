package mohamad.ft

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mohamad.ft.databinding.UserItemBinding
import mohamad.ft.db.UserEntity


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder =
        UserViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    var onItemClick: ((id: Int) -> Unit)? = null
    var onItemLongClick: ((id: Int) -> Unit)? = null


    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(userEntity: UserEntity) {
            binding.apply {
                tvName.text = "Name : ${userEntity.name}"
                tvAge.text = "Age : ${userEntity.age}"
                cardRoot.apply {
                    setOnLongClickListener {
                        onItemLongClick?.invoke(userEntity.id)
                        true
                    }
                    setOnClickListener { onItemClick?.invoke(userEntity.id) }
                }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem == newItem
    }
    val differ = AsyncListDiffer(this, differCallback)
}