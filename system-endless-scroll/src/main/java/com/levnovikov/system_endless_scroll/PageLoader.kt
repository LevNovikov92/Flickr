package com.levnovikov.system_endless_scroll


/**
 * Author: lev.novikov
 * Date: 22/5/18.
 */

interface PageLoader {
    fun clearData()
    fun getItemCount(): Int
    fun loadNextPage(page: Int,
                     onSuccess: (totalPages: Int) -> Unit,
                     onError: (e: Exception) -> Unit)
}