/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.conexion.Conexion;
import com.modelo.Persona;
import java.sql.*;
import java.util.*;

/**
 *
 * @author KamiVela
 */
public class ControlPersona extends Conexion {

    public void insertar(Persona per) throws Exception {
        try {
            this.conectar();
            String sql = "insert into personas ( nombre, edad, genero, direccion)"
                    + " values(?,?,?,?)";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, per.getNombre());
            pre.setInt(2, per.getEdad());
            pre.setString(3, per.getGenero());
            pre.setString(4, per.getDireccion());
            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void modificar(Persona per) throws Exception {
        try {
            this.conectar();
            String sql = "update personas set nombre=?, edad=?,genero=?, direccion=? where Id=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, per.getNombre());
            pre.setInt(2, per.getEdad());
            pre.setString(3, per.getGenero());
            pre.setString(4, per.getDireccion());
            pre.setInt(5, per.getId());
            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Persona per) throws Exception {
        try {
            this.conectar();
            String sql = "delete from personas where id=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setInt(1, per.getId());
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
            String sql = "select * from personas";
            PreparedStatement pre = this.getCon().prepareCall(sql);
            res = pre.executeQuery();
            while (res.next()) {
                Persona per = new Persona();
                per.setId(res.getInt("Id"));
                per.setNombre(res.getString("Nombre"));
                per.setEdad(res.getInt("Edad"));
                per.setGenero(res.getString("Genero"));
                per.setDireccion(res.getString("Direccion"));
                lista.add(per);

            }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
