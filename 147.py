def process_and_sum(lst):
    result = []
    for i, val in enumerate(lst):
        if i % 3 == 0:
            result.append(val ** 2)
        elif i % 4 == 0 and i % 3 != 0:
            result.append(val ** 3)
        else:
            result.append(val)
    return sum(result)

def faulty_process_and_sum(lst):
    result = []
    for i, val in enumerate(lst):
        if i % 3 == 0:
            result.append(val ** 2)
        elif i % 5 == 0 and i % 3 != 0:
            result.append(val ** 3)
        else:
            result.append(val)
    return sum(result)

if __name__ == "__main__":
    input_list = [1,2,3,4,5 ]
    result = process_and_sum(input_list)
    print(f"The result is: {result}")
    faulty_result = faulty_process_and_sum(input_list)
    print(f"The faulty result is: {faulty_result}")