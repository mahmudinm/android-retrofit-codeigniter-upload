<?php

defined('BASEPATH') OR exit('No direct script access allowed');


class Welcome extends BD_Controller {

    function __construct()
    {
        // Construct the parent class
        parent::__construct();
        $this->load->database();
    }

    public function index_get()
    {
        $this->response('ok', 200);
    }

}
