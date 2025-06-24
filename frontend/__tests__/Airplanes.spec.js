import { mount, flushPromises } from '@vue/test-utils'
import axios from 'axios'
import Airplane from '../src/components/Airplane.vue'

vi.mock('axios')

describe('Airplane.vue', () => {
    const mockAirplanes = [
        { id: 1, model: 'Boeing 737', prod_year: 2020, in_flight: false },
        { id: 2, model: 'Airbus A320', prod_year: 2022, in_flight: true }
    ]

    beforeEach(() => {
        axios.get.mockResolvedValue({ data: mockAirplanes })
        axios.post.mockResolvedValue({})
    })

    it('отображает список самолётов', async () => {
        const wrapper = mount(Airplane, { props: { msg: 'Test Message' } })
        await flushPromises()
        expect(wrapper.text()).toContain('Boeing 737')
        expect(wrapper.text()).toContain('Airbus A320')
    })

    it('создаёт самолёт', async () => {
        const wrapper = mount(Airplane, { props: { msg: 'Test' } })
        await flushPromises()
        await wrapper.find('input[type="text"]').setValue('TestPlane')
        await wrapper.find('input[type="number"]').setValue(2025)
        await wrapper.findAll('button')[0].trigger('click') // "Добавить"

        expect(axios.post).toHaveBeenCalledWith('/api/airplanes', {
            name: 'TestPlane',
            prod_year: 2025
        })
    })

    it('отправляет самолёт в полёт', async () => {
        const wrapper = mount(Airplane, { props: { msg: 'Test' } })
        await flushPromises()
        const takeOffButtons = wrapper.findAll('button')
        const takeOffButton = takeOffButtons.find(btn => btn.text() === 'Взлет')
        await takeOffButton.trigger('click')
        expect(axios.post).toHaveBeenCalledWith('/api/airplanes/flight/take_off/1')
    })

    it('садит самолёт', async () => {
        const wrapper = mount(Airplane, { props: { msg: 'Test' } })
        await flushPromises()
        const landButtons = wrapper.findAll('button')
        const landButton = landButtons.find(btn => btn.text() === 'Посадка')
        await landButton.trigger('click')
        expect(axios.post).toHaveBeenCalledWith('/api/airplanes/flight/land/2')
    })

    it('удаляет самолёт', async () => {
        const wrapper = mount(Airplane, { props: { msg: 'Test' } })
        await flushPromises()
        const deleteButtons = wrapper.findAll('button')
        const deleteButton = deleteButtons.find(btn => btn.text() === 'Удалить')
        await deleteButton.trigger('click')
        expect(axios.get).toHaveBeenCalledWith('/api/airplanes/1')
    })
})
