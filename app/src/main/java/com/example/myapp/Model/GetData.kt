package com.example.myapp.Model

data class GetData (
      val mt_motivo: String,
      val dm_observacion: String,
      val dm_fcreacion: String,
      val des_hora_asignacion: String,
      val dm_id: Int,
      val rt_numero_ruta: Int,
      val rt_orden: Int,
      val rt_id: Int,
      val rt_tiempo: String,
      val cliente: String,
      val rt_inicio: String,
      val rt_estado: Int,
      val des_id_estado: Int,
      var dir_direccion: String,
      var telefono: String,
      var latlong_cli: String,
      var codigo_dm: String
)

