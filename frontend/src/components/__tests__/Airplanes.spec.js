import { mount, flushPromises } from '@vue/test-utils'
vi.mock('axios')

import axios from 'axios'
import Airplane from '../Airplane.vue'

describe('Airplane.vue', () => {
    const mockAirplanes = [
        { id: 1, model: 'Boeing 737', prod_year: 2020, in_flight: false },
        { id: 2, model: 'Airbus A320', prod_year: 2022, in_flight: true }
    ]

    beforeEach(() => {
        axios.get.mockResolvedValue({ data: mockAirplanes })
    })

    it('отображает список самолётов', async () => {
        const wrapper = mount(Airplane, {
            props: { msg: 'Test Message' }
        })

        await flushPromises()

        expect(wrapper.text()).toContain('Boeing 737')
        expect(wrapper.text()).toContain('Airbus A320')
    })

    it('создаёт самолёт', async () => {
        axios.post.mockResolvedValue({})
        const wrapper = mount(Airplane, {
            props: { msg: 'Test' }
        })

        await flushPromises()

        await wrapper.find('input[type="text"]').setValue('TestPlane')
        await wrapper.find('input[type="number"]').setValue(2025)

        const buttons = wrapper.findAll('button')
        await buttons[0].trigger('click')

        expect(axios.post).toHaveBeenCalledWith('/api/airplanes', {
            name: 'TestPlane',
            prod_year: 2025
        })
    })
})