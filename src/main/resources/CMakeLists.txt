cmake_minimum_required(VERSION 3.27)
project(smart_calc)

set(CMAKE_CXX_STANDARD 17)

include_directories(../cpp/credit)
include_directories(../cpp/deposit)
include_directories(../cpp/smart_calc)

add_library(smart_calc SHARED
        ../cpp/credit/credit_calc_model.cc
        ../cpp/credit/credit_calc_model.h
        ../cpp/deposit/deposit_calc_model.cc
        ../cpp/deposit/deposit_calc_model.h
        ../cpp/smart_calc/smart_calc_model.cc
        ../cpp/smart_calc/smart_calc_model.h)