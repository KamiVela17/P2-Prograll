/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.conexion.Conexion;
import com.modelo.Usuario;
import java.sql.*;
import java.util.*;

/**
 *
 * @author KamiVela
 */
public class ControlUsuario extends Conexion {

    public void insertar(Usuario user) throws Exception {
        try {
            this.conectar();
            String sql = "insert into usuarios ( usuario, contraseña)"
                    + " values(?,?)";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, user.getUsuario());
            pre.setString(2, user.getContraseña());

            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void modificar(Usuario user) throws Exception {
        try {
            this.conectar();
            String sql = "update usuarios set usuario=?,contraseña=? where id=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, user.getUsuario());
            pre.setString(2, user.getContraseña());
            pre.setInt(3, user.getId());
            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Usuario user) throws Exception {
        try {
            this.conectar();
            String sql = "delete from usuarios where id=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setInt(1, user.getId());
            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public List mostrar() throws Exception {
        ResultSet res;
        List lista = new ArrayList();
        try {
            this.conectar();
            String sql = "select * from usuarios";
            PreparedStatement pre = this.getCon().prepareCall(sql);
            res = pre.executeQuery();
            while (res.next()) {
                Usuario user = new Usuario();
                user.setId(res.getInt("Id"));
                user.setUsuario(res.getString("Usuario"));
                user.setContraseña(res.getString("Contraseña"));

                lista.add(user);

            }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
