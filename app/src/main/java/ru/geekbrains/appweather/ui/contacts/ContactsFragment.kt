package ru.geekbrains.appweather.ui.contacts

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.geekbrains.appweather.R
import ru.geekbrains.appweather.databinding.FragmentContactsBinding

const val REQUEST_CONTACTS = 42
const val REQUEST_CALL = 43

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private var selectedNumber: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission(REQUEST_CONTACTS)
    }

    private fun checkPermission(code: Int) {
        when (code) {
            REQUEST_CONTACTS -> {
                context?.let {
                    when {
                        ContextCompat.checkSelfPermission(it,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED -> { getContacts() }
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                            AlertDialog.Builder(it)
                                .setTitle("Доступ к контактам")
                                .setMessage("Объяснение")
                                .setPositiveButton("Предоставить доступ") { _, _ -> requestPermission(code) }
                                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                                .create()
                                .show()
                        }
                        else -> { requestPermission(code) }
                    }
                }
            }
            REQUEST_CALL -> {
                context?.let {
                    when {
                        ContextCompat.checkSelfPermission(it,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED -> { makeCall() }
                        shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE) -> {
                            AlertDialog.Builder(it)
                                .setTitle("Совершение вызовов")
                                .setMessage("Объяснение")
                                .setPositiveButton("Предоставить доступ") { _, _ -> requestPermission(code) }
                                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                                .create()
                                .show()
                        }
                        else -> { requestPermission(code) }
                    }
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getContacts()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Доступ к контактам")
                            .setMessage("Объяснение")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                    return
                }
            REQUEST_CALL -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    makeCall()
                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle("Совершение вызовов")
                            .setMessage("Объяснение")
                            .setNegativeButton("Закрыть") { dialog, _ -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                }
                return
            }
        }
    }

    private fun getContacts() {
        context?.let {
            // Получаем ContentResolver у контекста
            val contentResolver: ContentResolver = it.contentResolver
            // Отправляем запрос на получение контактов и получаем ответ в виде Cursor'а
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor'е
                    if (cursor.moveToPosition(i)) {
                        // Берём из Cursor'а столбец с именем
                        val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        addView(it, name, id)
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun makeCall() {
        val intent = Intent(Intent.ACTION_CALL);
        intent.data = Uri.parse("tel:$selectedNumber")
        Toast.makeText(context, "Call to $selectedNumber", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }

    private fun addView(context: Context, textToShow: String, id: String) {
        binding.containerForContacts.addView(AppCompatTextView(context).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.main_container_text_size)
            setOnClickListener {
                selectedNumber = getPhoneByID(id)
                checkPermission(REQUEST_CALL)
            }
        })
    }

    @SuppressLint("Recycle")
    fun getPhoneByID (id: String): String {
        val contentResolver: ContentResolver? = context?.contentResolver
        val phoneCursor: Cursor? = contentResolver?.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
        )
        var phone = ""
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                phone = phoneCursor.getString(
                    phoneCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
            }
        }
        return phone
    }

    private fun requestPermission(code: Int) {
        when (code) {
            REQUEST_CONTACTS -> requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), code)
            REQUEST_CALL -> requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), code)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactsFragment()
    }
}