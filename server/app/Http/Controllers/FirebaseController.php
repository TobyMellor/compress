<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\FirebaseToken;

class FirebaseController extends Controller {
    public function store_token(Request $request) {
        $firebaseToken = new FirebaseToken([
            'token' => $request->input('token')
        ]);

        $firebaseToken->save();

        return response()->json([
            'success' => true,
            'message' => 'Nice!'
        ]);
    }
}