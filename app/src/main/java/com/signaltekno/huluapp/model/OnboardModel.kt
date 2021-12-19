package com.signaltekno.huluapp.model

import androidx.annotation.DrawableRes
import com.signaltekno.huluapp.R

sealed class OnboardModel(
    @DrawableRes val icon: Int,
    val title: String,
    val desc: String
){
    object Board1: OnboardModel(icon = R.drawable.cartoon1, title = "Rekomendasi Film", desc = "Rekomendasi film mingguan terbaik untuk kamu tonton")
    object Board2: OnboardModel(icon = R.drawable.cinema1, title = "Pilih Genre", desc = "Pilih genre yang ingin kamu tonton dan lihat daftar rekomendasi")
    object Board3: OnboardModel(icon = R.drawable.popcorn1, title = "Detail Film", desc = "Pilih dan lihat detail film yang ingin kamu tonton")
}
