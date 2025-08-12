package com.example.wingpediafrontend_ipc

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object WingpediaDatabase {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://hbkvpgmuzmteqnbmcbhf.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imhia3ZwZ211em10ZXFuYm1jYmhmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTQ1MDQwODMsImV4cCI6MjA3MDA4MDA4M30.pGTAXsSMd5iqpLB42T9rayS1FnXgcEVP4pZ4I_44DKA"
    ) {
        install(Auth)
        install(Postgrest)
        //install other modules
    }
}
