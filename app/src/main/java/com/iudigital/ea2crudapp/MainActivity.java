package com.iudigital.ea2crudapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Atributos
    EditText txtTipoDocumento, txtNumeroDocumento, txtNombre, txtApellido, txtCorreo, txtTelefono;
    Button btnConsultar, btnCrear, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asociamos los EditText con los elementos del layout
        txtTipoDocumento = findViewById(R.id.txtTipoDocumento);
        txtNumeroDocumento = findViewById(R.id.txtNumeroDocumento);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);

        // Asociamos los botones con los elementos del layout
        btnConsultar = findViewById(R.id.btnConsultar);
        btnCrear = findViewById(R.id.btnCrear);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Seteamos los listeners para los botones CRUD
        btnConsultar.setOnClickListener(this::getUsers);
        btnCrear.setOnClickListener(this::createUsers);
        btnActualizar.setOnClickListener(this::updateUser);
        btnEliminar.setOnClickListener(this::deleteUser);
    }

    // Método para consultar un usuario basado en el número de documento
    public void getUsers(View view) {
        String numeroDocumento = txtNumeroDocumento.getText().toString();

        if (!numeroDocumento.isEmpty()) {
            List<Usuario> usuarios = Usuario.find(Usuario.class, "numero_documento = ?", numeroDocumento);

            if (!usuarios.isEmpty()) {
                Usuario usuario = usuarios.get(0); // Asumiendo que hay solo un usuario con este documento
                txtTipoDocumento.setText(usuario.getTipoDocumento());
                txtNombre.setText(usuario.getNombre());
                txtApellido.setText(usuario.getApellido());
                txtCorreo.setText(usuario.getCorreo());
                txtTelefono.setText(usuario.getTelefono());

                Toast.makeText(MainActivity.this, "Usuario encontrado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
                limpiarCampos();
            }
        } else {
            Toast.makeText(MainActivity.this, "Por favor, ingrese un número de documento para consultar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para crear un nuevo usuario
    public void createUsers(View view) {
        String tipoDocumento = txtTipoDocumento.getText().toString();
        String numeroDocumento = txtNumeroDocumento.getText().toString();
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String telefono = txtTelefono.getText().toString();

        if (!tipoDocumento.isEmpty() && !numeroDocumento.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !telefono.isEmpty()) {
            // Crear un nuevo usuario en la base de datos
            Usuario usuario = new Usuario(tipoDocumento, numeroDocumento, nombre, apellido, correo, telefono);
            usuario.save(); // Guardar en la base de datos

            Toast.makeText(MainActivity.this, "Usuario creado: " + nombre + " " + apellido, Toast.LENGTH_LONG).show();
            limpiarCampos();
        } else {
            Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para actualizar un usuario basado en el número de documento
    public void updateUser(View view) {
        String numeroDocumento = txtNumeroDocumento.getText().toString();

        if (!numeroDocumento.isEmpty()) {
            List<Usuario> usuarios = Usuario.find(Usuario.class, "numero_documento = ?", numeroDocumento);

            if (!usuarios.isEmpty()) {
                Usuario usuario = usuarios.get(0);
                usuario.setTipoDocumento(txtTipoDocumento.getText().toString());
                usuario.setNombre(txtNombre.getText().toString());
                usuario.setApellido(txtApellido.getText().toString());
                usuario.setCorreo(txtCorreo.getText().toString());
                usuario.setTelefono(txtTelefono.getText().toString());
                usuario.save(); // Guardar los cambios

                Toast.makeText(MainActivity.this, "Usuario actualizado", Toast.LENGTH_LONG).show();
                limpiarCampos();
            } else {
                Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Por favor, ingrese un número de documento para actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para eliminar un usuario basado en el número de documento
    public void deleteUser(View view) {
        String numeroDocumento = txtNumeroDocumento.getText().toString();

        if (!numeroDocumento.isEmpty()) {
            List<Usuario> usuarios = Usuario.find(Usuario.class, "numero_documento = ?", numeroDocumento);

            if (!usuarios.isEmpty()) {
                Usuario usuario = usuarios.get(0);
                usuario.delete();

                Toast.makeText(MainActivity.this, "Usuario eliminado", Toast.LENGTH_LONG).show();
                limpiarCampos();
            } else {
                Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Por favor, ingrese un número de documento para eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para limpiar los campos
    private void limpiarCampos() {
        txtTipoDocumento.setText("");
        txtNumeroDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }
}
