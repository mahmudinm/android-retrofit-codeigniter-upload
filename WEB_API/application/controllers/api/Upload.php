<?php

defined('BASEPATH') OR exit('No direct script access allowed');


class Upload extends BD_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->database();
    }


    public function index_post()
    {

        $config['upload_path'] = './upload/';
        $config['allowed_types'] = 'gif|jpg|png|jpeg';
        $config['encrypt_name'] = TRUE;
        $config['max_size'] = '6000000';
        $config['max_width']  = '10024';
        $config['max_height']  = '5768';

        //load codeigniter upload helper/libary/magic thing
        $this->load->library('upload', $config);

        if ( ! $this->upload->do_upload('gambar'))
        {
          //upload error, return false
            $response['status'] = $this->upload->display_errors();
            return $this->response($response, 200);
        }
        else
        {
          //upload success, return uploaded file name
            $gambar = array('upload_data' => $this->upload->data());
            $file_name = $gambar['upload_data']['file_name'];
            $data = [
                'gambar' => $file_name
            ];

            $insert = $this->db->insert('gambar', $data);

            if ($insert) {
                $response['status'] = 'success';
            } else {
                $response['status'] = 'failed';
            }
            return $this->response($response, 200);

        }
    }

}
