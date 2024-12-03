package com.example.contactlistexample

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contactlistexample.adapter.ContactAdapter
import com.example.contactlistexample.data.Contact
import com.google.android.material.floatingactionbutton.FloatingActionButton
class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ContactAdapter
    private val contactList = mutableListOf<Contact>()
    private var filteredList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bindeando vistas
        val etName = findViewById<EditText>(R.id.etName)
        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber)
        val cbAvailability = findViewById<CheckBox>(R.id.checkboxAvailability)
        val fabAddContact = findViewById<FloatingActionButton>(R.id.fabAddContact)
        val fabFilter = findViewById<FloatingActionButton>(R.id.fabFilter)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Configuración inicial del RecyclerView y adapter
        adapter = ContactAdapter(filteredList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Listener del FAB para agregar un contacto
        fabAddContact.setOnClickListener {
            // Obtener valores ingresados por el usuario
            val name = etName.text.toString().trim()
            val phone = etPhoneNumber.text.toString().trim()
            val isAvailable = cbAvailability.isChecked

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                // Crear un nuevo contacto y añadirlo a la lista
                val contact = Contact(name, phone, isAvailable)
                contactList.add(contact)
                filteredList.add(contact) // También agregarlo al filtro

                // Notificar al adapter que la lista cambió
                adapter.notifyItemInserted(filteredList.size - 1)

                // Limpiar los campos después de añadir
                etName.text.clear()
                etPhoneNumber.text.clear()
                cbAvailability.isChecked = false
            }
        }

        // Listener del FAB para filtrar los contactos
        fabFilter.setOnClickListener {
            // Filtrar los contactos con campo boolean establecido a true
            filteredList.clear()
            filteredList.addAll(contactList.filter { it.isAvailable })
            adapter.notifyDataSetChanged() // Notificar al adapter que los datos fueron filtrados
        }
    }
}
