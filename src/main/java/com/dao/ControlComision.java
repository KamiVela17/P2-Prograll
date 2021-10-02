/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.conexion.Conexion;
import com.modelo.Comision;
import java.sql.*;
import java.util.*;

/**
 *
 * @author KamiVela
 */
public class ControlComision extends Conexion {

    public void insertar(Comision comision) throws Exception {
        try {
            this.conectar();
            String sql = "insert into comisiones (Personas_Id, Usuarios_Id, Enero, Febrero, Marzo, Promedio, Total)"
                    + " values((select id from personas where nombre=?),(select id from usuarios where usuario=?),?,?,?,?,?)";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, comision.getPersona());
            pre.setString(2, comision.getUsuario());
            pre.setDouble(3, comision.getEnero());
            pre.setDouble(4, comision.getFebrero());
            pre.setDouble(5, comision.getMarzo());
            pre.setDouble(6, comision.getPromedio());
            pre.setDouble(7, comision.getTotal());
            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void modificar(Comision comision) throws Exception {
        try {
            this.conectar();
            String sql = "update comisiones set Personas_Id=(select id from personas where nombre=?),Usuarios_Id=(select id from usuarios where usuario=?), Enero=?,Febrero=?, Marzo=?, Promedio=?,Total=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setString(1, comision.getPersona());
            pre.setString(2, comision.getUsuario());
            pre.setDouble(3, comision.getEnero());
            pre.setDouble(4, comision.getFebrero());
            pre.setDouble(5, comision.getMarzo());
            pre.setDouble(6, comision.getPromedio());
            pre.setDouble(7, comision.getTotal());

            pre.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(Comision comision) throws Exception {
        try {
            this.conectar();
            String sql = "delete from comisiones where id=?";
            PreparedStatement pre = this.getCon().prepareStatement(sql);
            pre.setInt(1, comision.getId());
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
            String sql = "select * from comisiones";
            PreparedStatement pre = this.getCon().prepareCall(sql);
            res = pre.executeQuery();
            while (res.next()) {
                Comision comision = new Comision();
                comision.setId(res.getInt("Id"));
                comision.setPersona(res.getString("Personas_Id"));
                comision.setUsuario(res.getString("Usuarios_Id"));
                comision.setEnero(res.getDouble("Enero"));
                comision.setFebrero(res.getDouble("Febrero"));
                comision.setMarzo(res.getDouble("Marzo"));
                comision.setPromedio(res.getDouble("Promedio"));
                comision.setTotal(res.getDouble("Total"));
                lista.add(comision);

            }
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
