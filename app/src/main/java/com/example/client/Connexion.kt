package com.example.client

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class Connexion : Fragment(R.layout.fragment_connexion) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val connectButton = view.findViewById<Button>(R.id.connectButton)
        connectButton.setOnClickListener {
            findNavController().navigate(R.id.action_Connexion_to_edit_program)
        }
    }
}
