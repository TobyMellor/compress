<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\FirebaseToken;

class FirebaseController extends Controller {
    public function store_token(Request $request) {
        $token = new Token([
            'token' => $request->input('token')
        ]);

        $token->save();

        return response()->json([
            'success' => true,
            'message' => 'Nice!'
        ]);
    }
}